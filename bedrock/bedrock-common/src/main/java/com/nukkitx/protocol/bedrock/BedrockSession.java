package com.nukkitx.protocol.bedrock;

import com.nukkitx.natives.aes.Aes;
import com.nukkitx.natives.aes.AesFactory;
import com.nukkitx.natives.sha256.Sha256;
import com.nukkitx.natives.util.Natives;
import com.nukkitx.network.SessionConnection;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.MinecraftSession;
import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.compat.BedrockCompat;
import com.nukkitx.protocol.bedrock.exception.PacketSerializeException;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.handler.DefaultBatchHandler;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.DestroyFailedException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.zip.Deflater;

public abstract class BedrockSession implements MinecraftSession<BedrockPacket> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockSession.class);
    private static final AesFactory AES_FACTORY = Natives.AES_CFB8.get();
    private static final ThreadLocal<Sha256> HASH_LOCAL = ThreadLocal.withInitial(Natives.SHA_256);

    private final Set<Consumer<DisconnectReason>> disconnectHandlers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Queue<BedrockPacket> queuedPackets = new ConcurrentLinkedQueue<>();
    private final AtomicLong sentEncryptedPacketCount = new AtomicLong();
    private final BedrockWrapperSerializer wrapperSerializer;
    final SessionConnection<ByteBuf> connection;
    private BedrockPacketCodec packetCodec = BedrockCompat.COMPAT_CODEC;
    private BedrockPacketHandler packetHandler;
    private BatchHandler batchHandler = DefaultBatchHandler.INSTANCE;
    private Aes encryptionCipher = null;
    private Aes decryptionCipher = null;
    private SecretKey agreedKey;
    private int compressionLevel = Deflater.DEFAULT_COMPRESSION;
    private volatile boolean closed = false;
    private volatile boolean logging = true;

    BedrockSession(SessionConnection<ByteBuf> connection, BedrockWrapperSerializer serializer) {
        this.connection = connection;
        this.wrapperSerializer = serializer;
    }

    public void setPacketHandler(@Nonnull BedrockPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void setPacketCodec(BedrockPacketCodec packetCodec) {
        this.packetCodec = Objects.requireNonNull(packetCodec, "packetCodec");
    }

    void checkForClosed() {
        if (this.closed) {
            throw new IllegalStateException("Connection has been closed");
        }
    }

    @Override
    public void sendPacket(@Nonnull BedrockPacket packet) {
        this.checkPacket(packet);

        this.queuedPackets.add(packet);
    }

    @Override
    public void sendPacketImmediately(@Nonnull BedrockPacket packet) {
        this.checkPacket(packet);

        this.sendWrapped(Collections.singletonList(packet), !packet.getClass().isAnnotationPresent(NoEncryption.class));
    }

    private void checkPacket(BedrockPacket packet) {
        this.checkForClosed();
        Objects.requireNonNull(packet, "packet");

        if (log.isTraceEnabled() && this.logging) {
            String to = this.connection.getAddress().toString();
            log.trace("Outbound {}: {}", to, packet);
        }

        Preconditions.checkState(this.packetCodec != BedrockCompat.COMPAT_CODEC, "No PacketCodec is set!");

        // Verify that the packet ID exists.
        this.packetCodec.getId(packet.getClass());
    }

    public void sendWrapped(Collection<BedrockPacket> packets, boolean encrypt) {
        ByteBuf compressed = ByteBufAllocator.DEFAULT.ioBuffer();
        try {
            this.wrapperSerializer.serialize(compressed, this.packetCodec, packets, this.compressionLevel);
            this.sendWrapped(compressed, encrypt);
        } catch (Exception e) {
            log.error("Unable to compress packets", e);
        } finally {
            if (compressed != null) {
                compressed.release();
            }
        }
    }

    public synchronized void sendWrapped(ByteBuf compressed, boolean encrypt) {
        Objects.requireNonNull(compressed, "compressed");
        ByteBuf withTrailer = null;
        try {
            int startIndex = compressed.readerIndex();
            ByteBuf finalPayload = ByteBufAllocator.DEFAULT.ioBuffer(compressed.readableBytes() + 9);
            finalPayload.writeByte(0xfe); // Wrapped packet ID
            if (this.encryptionCipher != null && encrypt) {
                withTrailer = ByteBufAllocator.DEFAULT.ioBuffer(compressed.readableBytes() + 8);
                compressed.readerIndex(startIndex);
                byte[] trailer = this.generateTrailer(compressed);
                compressed.readerIndex(startIndex);
                withTrailer.writeBytes(compressed);
                withTrailer.writeBytes(trailer);

                this.encryptionCipher.cipher(withTrailer.nioBuffer(), finalPayload.internalNioBuffer(1, withTrailer.readableBytes()));
                finalPayload.writerIndex(finalPayload.readerIndex()+withTrailer.readableBytes()+1);
            } else {
                finalPayload.writeBytes(compressed);
            }
            this.connection.send(finalPayload);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Unable to encrypt package", e);
        } finally {
            if (withTrailer != null) {
                withTrailer.release();
            }
        }
    }

    void onTick() {
        if (this.closed) {
            return;
        }

        this.sendQueued();
    }

    private void sendQueued() {
        BedrockPacket packet;
        List<BedrockPacket> toBatch = new ObjectArrayList<>();
        while ((packet = this.queuedPackets.poll()) != null) {
            if (packet.getClass().isAnnotationPresent(NoEncryption.class)) {
                // We hit a unencryptable packet. Send the current wrapper and then send the unencryptable packet.
                if (!toBatch.isEmpty()) {
                    this.sendWrapped(toBatch, true);
                    toBatch = new ObjectArrayList<>();
                }

                this.sendPacketImmediately(packet);
                continue;
            }

            toBatch.add(packet);
        }

        if (!toBatch.isEmpty()) {
            this.sendWrapped(toBatch, true);
        }
    }

    public synchronized void enableEncryption(@Nonnull SecretKey secretKey) {
        this.checkForClosed();
        log.debug("Encryption enabled.");
        Objects.requireNonNull(secretKey, "secretKey");
        if (!secretKey.getAlgorithm().equals("AES")) {
            throw new IllegalArgumentException("Invalid key algorithm");
        }
        if (this.encryptionCipher != null || this.decryptionCipher != null) {
            throw new IllegalStateException("Encryption has already been enabled");
        }

        this.agreedKey = secretKey;
        byte[] iv = Arrays.copyOf(secretKey.getEncoded(), 16);
        this.encryptionCipher = AES_FACTORY.get(true, secretKey, new IvParameterSpec(iv));
        this.decryptionCipher = AES_FACTORY.get(false, secretKey, new IvParameterSpec(iv));
    }

    private byte[] generateTrailer(ByteBuf buf) {
        Sha256 hash = HASH_LOCAL.get();
        ByteBuf counterBuf = null;
        ByteBuf keyBuf = null;
        try {
            counterBuf = ByteBufAllocator.DEFAULT.directBuffer(8);
            keyBuf = ByteBufAllocator.DEFAULT.directBuffer(this.agreedKey.getEncoded().length);
            counterBuf.writeLongLE(this.sentEncryptedPacketCount.getAndIncrement());
            keyBuf.writeBytes(this.agreedKey.getEncoded());

            hash.update(counterBuf.internalNioBuffer(0, 8));
            hash.update(buf.nioBuffer());
            hash.update(keyBuf.nioBuffer());
            byte[] digested = hash.digest();
            return Arrays.copyOf(digested, 8);
        } finally {
            if (counterBuf != null) {
                counterBuf.release();
            }
            if (keyBuf != null) {
                keyBuf.release();
            }
        }
    }

    public boolean isEncrypted() {
        return encryptionCipher != null;
    }

    public abstract void disconnect();

    void close(DisconnectReason reason) {
        checkForClosed();
        this.closed = true;
        // Free native resources if required
        if (this.encryptionCipher != null) {
            this.encryptionCipher.free();
        }
        if (this.decryptionCipher != null) {
            this.decryptionCipher.free();
        }

        // Destroy secret key
        if (this.agreedKey != null && !this.agreedKey.isDestroyed()) {
            try {
                this.agreedKey.destroy();
            } catch (DestroyFailedException e) {
                // Ignore - throws exception by default
            }
        }
        for (Consumer<DisconnectReason> disconnectHandler : this.disconnectHandlers) {
            disconnectHandler.accept(reason);
        }
    }

    public void onWrappedPacket(final ByteBuf wrappedData) {
        ByteBuf batched = null;
        try {
            if (this.isEncrypted()) {
                // Decryption
                batched = ByteBufAllocator.DEFAULT.directBuffer(wrappedData.readableBytes());
                this.decryptionCipher.cipher(wrappedData.nioBuffer(), batched.internalNioBuffer(0, wrappedData.readableBytes()));

                // TODO: Maybe verify the checksum?
                batched.writerIndex(wrappedData.readableBytes()+1-8);
            } else {
                // Encryption not enabled so it should be readable.
                batched = wrappedData;
            }
            batched.markReaderIndex();

            List<BedrockPacket> packets = new ObjectArrayList<>();
            this.wrapperSerializer.deserialize(batched, this.packetCodec, packets);

            this.batchHandler.handle(this, batched, packets);
        } catch (GeneralSecurityException ignore) {
        } catch (PacketSerializeException e) {
            log.warn("Error whilst decoding packets", e);
        } finally {
            if (batched != null && batched != wrappedData) {
                batched.release();
            }
        }
    }

    public InetSocketAddress getAddress() {
        return this.connection.getAddress();
    }

    public boolean isClosed() {
        return this.connection.isClosed();
    }

    public BedrockPacketCodec getPacketCodec() {
        return this.packetCodec;
    }

    public BedrockPacketHandler getPacketHandler() {
        return this.packetHandler;
    }

    public BatchHandler getBatchHandler() {
        return this.batchHandler;
    }

    public void setBatchHandler(BatchHandler batchHandler) {
        this.batchHandler = Objects.requireNonNull(batchHandler, "batchHandler");
    }

    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    public int getCompressionLevel() {
        return compressionLevel;
    }

    public boolean isLogging() {
        return this.logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public void addDisconnectHandler(Consumer<DisconnectReason> disconnectHandler) {
        Objects.requireNonNull(disconnectHandler, "disconnectHandler");
        this.disconnectHandlers.add(disconnectHandler);
    }

    @Override
    public long getLatency() {
        return this.connection.getPing();
    }

//    @ParametersAreNonnullByDefault
//    abstract class BedrockSessionListener implements RakNetSessionListener {
//
//        @Override
//        public void onEncapsulated(EncapsulatedPacket packet) {
//            if (BedrockSession.this.connection.getState() != RakNetState.CONNECTED) {
//                // We shouldn't be receiving packets till the connection is full established.
//                return;
//            }
//            ByteBuf buffer = packet.getBuffer();
//
//            int packetId = buffer.readUnsignedByte();
//            if (packetId == 0xfe /* Wrapper packet */) {
//                BedrockSession.this.onWrappedPacket(buffer);
//            }
//        }
//
//        @Override
//        public void onDirect(ByteBuf buf) {
//            // We shouldn't be receiving direct datagram messages from the client whilst they are connected.
//        }
//    }
}
