package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.BlackholeInboundAdapter;
import org.cloudburstmc.protocol.bedrock.netty.codec.FrameIdCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.batch.BedrockBatchDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionStrategy;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionEncoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.packet.BedrockPacketCodec;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockChannelInitializer;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;

import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Bedrock peer that represents a single network connection to the remote peer.
 * It can hold one or more {@link BedrockSession}s.
 */
public class BedrockPeer extends ChannelInboundHandlerAdapter {

    public static final String NAME = "bedrock-peer";

    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPeer.class);

    static final String BATCH_FLUSH_DELAY_PROPERTY = "org.cloudburstmc.protocol.bedrock.batchFlushDelayMillis";
    static final int DEFAULT_BATCH_FLUSH_DELAY_MILLIS = 1;
    static final int MAX_BATCH_FLUSH_DELAY_MILLIS = 50;

    /**
     * How long the first packet of a burst waits in the outbound queue for the rest
     * of that burst to join its batch. A producer that hands its packets over
     * together still gets a single batch at any positive delay; the wait only
     * splits bursts that are emitted over a longer stretch than the window, and
     * each resulting batch is then compressed on its own. A wider window trades
     * latency for compression efficiency on such producers, while zero disables
     * coalescing across event loop iterations. Configurable in milliseconds with
     * the {@value #BATCH_FLUSH_DELAY_PROPERTY} system property, read once when this
     * class is initialized.
     * <p>
     * Note that a window is not equivalent to the periodic flush this replaced: it
     * starts at the first enqueue, so a burst handed over together always waits the
     * full window, where a periodic tick of the same period waited half of it on
     * average.
     */
    static final long BATCH_FLUSH_DELAY_NANOS = TimeUnit.MILLISECONDS.toNanos(
            resolveFlushDelayMillis(SystemPropertyUtil.getInt(BATCH_FLUSH_DELAY_PROPERTY, DEFAULT_BATCH_FLUSH_DELAY_MILLIS)));
    static final long REJECTED_RETRY_DELAY_MILLIS = 10;

    protected final Int2ObjectMap<BedrockSession> sessions = new Int2ObjectOpenHashMap<>();
    protected final Queue<BedrockPacketWrapper> packetQueue = PlatformDependent.newMpscQueue();
    protected final Channel channel;
    protected final BedrockSessionFactory sessionFactory;
    protected final AtomicBoolean flushScheduled = new AtomicBoolean();
    protected final AtomicBoolean flushRetryScheduled = new AtomicBoolean();
    protected final AtomicBoolean closeRetryScheduled = new AtomicBoolean();
    protected AtomicBoolean closed = new AtomicBoolean();
    protected AtomicBoolean closing = new AtomicBoolean();

    public BedrockPeer(Channel channel, BedrockSessionFactory sessionFactory) {
        this.channel = channel;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the configured flush delay, falling back to the default when it is
     * outside the supported range. Out of range values are rejected rather than
     * saturated so a typo cannot silently pick the nearest bound.
     */
    static int resolveFlushDelayMillis(int delay) {
        if (delay < 0 || delay > MAX_BATCH_FLUSH_DELAY_MILLIS) {
            log.warn("Unable to use the flush delay system property '{}':{} - using the default value: {}",
                    BATCH_FLUSH_DELAY_PROPERTY, delay, DEFAULT_BATCH_FLUSH_DELAY_MILLIS);
            return DEFAULT_BATCH_FLUSH_DELAY_MILLIS;
        }
        return delay;
    }

    protected void onBedrockPacket(BedrockPacketWrapper wrapper) {
        if (this.closing.get()) {
            return;
        }
        int targetId = wrapper.getTargetSubClientId();
        BedrockSession session = this.sessions.computeIfAbsent(targetId, this::onSessionCreated);
        session.onPacket(wrapper);
    }

    protected BedrockSession onSessionCreated(int sessionId) {
        return this.sessionFactory.createSession(this, sessionId);
    }

    protected void checkForClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("Peer has been closed");
        }
    }

    protected void removeSession(BedrockSession session) {
        this.sessions.remove(session.subClientId, session);
    }

    protected void flushPacketQueue() {
        if (this.closed.get()) {
            this.flushScheduled.set(false);
            // Release anything enqueued after onClose() drained the queue.
            this.free();
            return;
        }
        if (this.closing.get()) {
            // Once a disconnect is requested nothing more is written; the queue
            // is freed by onClose(), which is guaranteed to follow.
            this.flushScheduled.set(false);
            return;
        }

        try {
            BedrockPacketWrapper packet;
            boolean wrotePacket = false;
            while ((packet = this.packetQueue.poll()) != null) {
                this.channel.write(packet);
                wrotePacket = true;
            }
            if (wrotePacket) {
                this.channel.flush();
            }
        } finally {
            this.flushScheduled.set(false);

            // A producer can enqueue after the final poll but before the flag is cleared.
            if (!this.closing.get() && !this.closed.get() && !this.packetQueue.isEmpty()) {
                this.schedulePacketFlush();
            }
        }
    }

    protected void schedulePacketFlush() {
        if (this.closed.get()) {
            // sendPacket() can race the door gate, enqueueing after onClose() has
            // already drained the queue and leaving the wrapper with no flush
            // task to release it. Hand the cleanup to the event loop rather than
            // draining here: the queue is MPSC, so drains must stay serialized.
            try {
                this.channel.eventLoop().execute(this::free);
            } catch (RejectedExecutionException exception) {
                // Rejection means the loop is shut down or its task queue is
                // full; either way it may still be running tasks, and other
                // producers can land here concurrently. free() is synchronized
                // to keep this drain mutually exclusive with those.
                this.free();
            }
            return;
        }
        // Once closing, nothing schedules; onClose() frees the queue.
        if (this.closing.get() || !this.flushScheduled.compareAndSet(false, true)) {
            return;
        }

        try {
            this.scheduleFlushTask();
        } catch (RejectedExecutionException exception) {
            // The loop refused the task: it is shutting down, or a bounded task
            // queue is momentarily full while the peer stays live. The wrapper
            // stays owned by the packet queue, but without a retry a live peer
            // with no further sends would strand it until close, so retry off
            // the global executor, which never rejects. The CAS keeps the retry
            // deduplicated to one chain per peer; otherwise every send during
            // saturation would spawn its own self reproducing retry, turning
            // loop backpressure into an unbounded global executor backlog. The
            // chain ends once the loop accepts a flush or the peer closes.
            this.flushScheduled.set(false);
            if (this.flushRetryScheduled.compareAndSet(false, true)) {
                this.scheduleRejectedRetry(this::retryPacketFlush);
            }
        } catch (RuntimeException exception) {
            this.flushScheduled.set(false);
            throw exception;
        }
    }

    protected void retryPacketFlush() {
        this.flushRetryScheduled.set(false);
        if (this.closing.get() || this.closed.get() || this.packetQueue.isEmpty()) {
            return;
        }
        this.schedulePacketFlush();
    }

    protected void scheduleFlushTask() {
        this.channel.eventLoop().schedule(this::flushPacketQueue, BATCH_FLUSH_DELAY_NANOS, TimeUnit.NANOSECONDS);
    }

    protected void executeOnEventLoop(Runnable task) {
        this.channel.eventLoop().execute(task);
    }

    protected boolean isOnEventLoop() {
        return this.channel.eventLoop().inEventLoop();
    }

    protected void scheduleRejectedRetry(Runnable retry) {
        GlobalEventExecutor.INSTANCE.schedule(retry, REJECTED_RETRY_DELAY_MILLIS, TimeUnit.MILLISECONDS);
    }

    protected void onRakNetDisconnect(ChannelHandlerContext ctx, RakDisconnectReason reason) {
        CharSequence disconnectReason = BedrockDisconnectReasons.getReason(reason);
        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = disconnectReason;
        }
    }

    /**
     * Releases every queued packet wrapper. The queue is MPSC, so drains must be
     * mutually exclusive. Most callers run on the event loop (onClose(), the
     * closed branches of flushPacketQueue() and schedulePacketFlush()), but when
     * the loop rejects work during shutdown a producer thread drains directly,
     * and the loop may still be running its final tasks at that point, so the
     * loop thread alone cannot serialize this. The lock does. Only close time
     * paths call this, never the flush hot path. Repeated invocation is only
     * safe because the drain is a destructive poll; iterating instead would
     * release the same wrappers twice.
     */
    protected synchronized void free() {
        BedrockPacketWrapper wrapper;
        while ((wrapper = this.packetQueue.poll()) != null) {
            ReferenceCountUtil.safeRelease(wrapper);
        }
    }

    public void sendPacket(int senderClientId, int targetClientId, BedrockPacket packet) {
        if (this.closing.get() || this.closed.get()) {
            ReferenceCountUtil.safeRelease(packet); // queue is no longer drained
            return;
        }
        this.packetQueue.add(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
        this.schedulePacketFlush();
    }

    public void sendPacket(BedrockPacketWrapper wrapper) {
        if (this.closing.get() || this.closed.get()) {
            ReferenceCountUtil.safeRelease(wrapper); // queue is no longer drained
            return;
        }
        this.packetQueue.add(wrapper);
        this.schedulePacketFlush();
    }

    public void sendPacketImmediately(int senderClientId, int targetClientId, BedrockPacket packet) {
        if (this.closing.get()) { // closed is covered by netty: writes to a closed channel are failed and released
            ReferenceCountUtil.safeRelease(packet);
            return;
        }
        this.channel.writeAndFlush(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
    }

    public void sendPacketsImmediately(int senderClientId, int targetClientId, BedrockPacket... packets) {
        if (this.closing.get()) {
            for (BedrockPacket packet : packets) {
                ReferenceCountUtil.safeRelease(packet);
            }
            return;
        }
        for (BedrockPacket packet : packets) {
            this.channel.write(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
        }
        this.channel.flush();
    }

    public void enableEncryption(@NonNull SecretKey secretKey) {
        Objects.requireNonNull(secretKey, "secretKey");
        if (!secretKey.getAlgorithm().equals("AES")) {
            throw new IllegalArgumentException("Invalid key algorithm");
        }
        // Check if the codecs exist in the pipeline
        if (this.channel.pipeline().get(BedrockEncryptionEncoder.class) != null ||
                this.channel.pipeline().get(BedrockEncryptionDecoder.class) != null) {
            throw new IllegalStateException("Encryption is already enabled");
        }

        int protocolVersion = this.getCodec().getProtocolVersion();
        boolean useCtr = protocolVersion >= Bedrock_v428.CODEC.getProtocolVersion();

        this.channel.pipeline().addAfter(FrameIdCodec.NAME, BedrockEncryptionEncoder.NAME,
                new BedrockEncryptionEncoder(secretKey, EncryptionUtils.createCipher(useCtr, true, secretKey)));
        this.channel.pipeline().addAfter(FrameIdCodec.NAME, BedrockEncryptionDecoder.NAME,
                new BedrockEncryptionDecoder(secretKey, EncryptionUtils.createCipher(useCtr, false, secretKey)));

        log.debug("Encryption enabled for {}", getSocketAddress());
    }

    public void setCompression(PacketCompressionAlgorithm algorithm) {
        Objects.requireNonNull(algorithm, "algorithm");
        this.setCompression(BedrockChannelInitializer.getCompression(algorithm, this.getRakVersion(), false));
    }

    public void setCompression(CompressionStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy");

        boolean needsPrefix = this.getCodec().getProtocolVersion() >= 649; // TODO: do not hardcode

        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (handler == null) {
            this.channel.pipeline().addBefore(BedrockBatchDecoder.NAME, CompressionCodec.NAME, new CompressionCodec(strategy, needsPrefix));
        } else {
            this.channel.pipeline().replace(CompressionCodec.NAME, CompressionCodec.NAME, new CompressionCodec(strategy, needsPrefix));
        }
    }

    public CompressionStrategy getCompressionStrategy() {
        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (!(handler instanceof CompressionCodec)) {
            return null;
        }
        return ((CompressionCodec) handler).getStrategy();
    }

    public BedrockCodec getCodec() {
        return this.channel.pipeline().get(BedrockPacketCodec.class).getCodec();
    }

    public BedrockCodecHelper getCodecHelper() {
        return this.channel.pipeline().get(BedrockPacketCodec.class).getHelper();
    }

    public void setCodec(BedrockCodec codec) {
        Objects.requireNonNull(codec, "codec");
        this.channel.pipeline().get(BedrockPacketCodec.class).setCodec(codec);
    }

    public void close(CharSequence reason) {
        if (this.isOnEventLoop()) {
            this.close0(reason, false);
            return;
        }
        // The session map is event loop confined, so the close must run there.
        // Rejection means the loop is shut down or a bounded task queue is full
        // while the channel may still be open, so retry off the never rejecting
        // global executor until the close lands or the channel dies on its own;
        // dropping the task would silently lose the disconnect reason. The CAS
        // keeps the retry deduplicated; the first reason wins.
        try {
            this.executeOnEventLoop(() -> this.close0(reason, false));
        } catch (RejectedExecutionException exception) {
            if (this.closeRetryScheduled.compareAndSet(false, true)) {
                this.scheduleRejectedRetry(() -> {
                    this.closeRetryScheduled.set(false);
                    if (!this.closing.get() && !this.closed.get() && this.channel.isOpen()) {
                        this.close(reason);
                    }
                });
            }
        }
    }

    protected void close0(CharSequence reason, boolean force) {
        if (!this.closing.compareAndSet(false, true)) {
            return;
        }

        this.blackholeInboundPackets(); // inbound stops either way

        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = reason;
        }

        if (force) {
            // If an exception is thrown or the peer is misbehaving, we close it immediately without notification
            this.channel.close();
        } else {
            this.channel.disconnect();
        }
    }

    protected void onClose() {
        if (this.channel.isOpen()) {
            log.warn("Tried to close peer, but channel is open!", new Throwable());
            return;
        }

        if (!this.closed.compareAndSet(false, true)) {
            return;
        }

        try {
            // Sessions remove themselves from the map in onClose(), and the open
            // addressing map must not be mutated mid iteration (skipped entries,
            // iterator NPE), so iterate a snapshot. An escaping exception here
            // could otherwise never be retried: closed is already latched.
            for (BedrockSession session : new ArrayList<>(this.sessions.values())) {
                try {
                    session.onClose();
                } catch (Exception e) {
                    log.error("Exception whilst closing session", e);
                }
            }
        } finally {
            this.sessions.clear();
            this.free();
        }
    }

    public boolean isConnected() {
        return !this.closed.get() && this.channel.isOpen();
    }

    public boolean isConnecting() {
        return !this.channel.isActive() && !this.closed.get();
    }

    public SocketAddress getSocketAddress() {
        return this.channel.remoteAddress();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public int getRakVersion() {
        return this.channel.config().getOption(RakChannelOption.RAK_PROTOCOL_VERSION);
    }

    /*
        Netty handler methods
     */

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        this.onClose();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.sessions.put(0, this.sessionFactory.createSession(this, 0));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.onClose();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof BedrockPacketWrapper) {
                this.onBedrockPacket((BedrockPacketWrapper) msg);
            } else {
                throw new DecoderException("Unexpected message type: " + msg.getClass().getName());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof RakDisconnectReason) {
            onRakNetDisconnect(ctx, (RakDisconnectReason) evt);
        }
    }

    protected void blackholeInboundPackets() {
        if (this.channel.pipeline().get(BlackholeInboundAdapter.class) == null) {
            this.channel.pipeline().addFirst(BlackholeInboundAdapter.NAME, BlackholeInboundAdapter.INSTANCE);
        }
    }
}
