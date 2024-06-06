package org.cloudburstmc.protocol.bedrock;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;
import org.cloudburstmc.protocol.common.PacketSignal;

import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BedrockSession {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockSession.class);

    private final AtomicBoolean closed = new AtomicBoolean();
    protected final BedrockPeer peer;
    protected final int subClientId;
    protected BedrockPacketHandler packetHandler;
    protected boolean logging;
    protected String disconnectReason = BedrockDisconnectReasons.UNKNOWN;

    public BedrockSession(BedrockPeer peer, int subClientId) {
        this.peer = peer;
        this.subClientId = subClientId;
    }

    public BedrockPacketHandler getPacketHandler() {
        return packetHandler;
    }

    public void setPacketHandler(@NonNull BedrockPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    protected void checkForClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("Session has been closed");
        }
    }

    public void sendPacket(@NonNull BedrockPacket packet) {
        this.peer.sendPacket(this.subClientId, 0, packet);
        this.logOutbound(packet);
    }

    public void sendPacketImmediately(@NonNull BedrockPacket packet) {
        this.peer.sendPacketImmediately(this.subClientId, 0, packet);
        this.logOutbound(packet);
    }

    public BedrockPeer getPeer() {
        return peer;
    }

    public BedrockCodec getCodec() {
        return this.peer.getCodec();
    }

    public void setCodec(BedrockCodec codec) {
        ObjectUtil.checkNotNull(codec, "codec");
        if (this.subClientId != 0) {
            throw new IllegalStateException("The packet codec can only be set by the primary session");
        }
        this.peer.setCodec(codec);
    }

    public void setCompression(PacketCompressionAlgorithm algorithm) {
        if (isSubClient()) {
            throw new IllegalStateException("The compression algorithm can only be set by the primary session");
        }
        this.peer.setCompression(algorithm);
    }

    public void enableEncryption(SecretKey key) {
        if (isSubClient()) {
            throw new IllegalStateException("Encryption can only be enabled by the primary session");
        }
        this.peer.enableEncryption(key);
    }

    public void close(String reason) {
        checkForClosed();

        if (isSubClient()) {
            // FIXME: Do sub-clients send a server-bound DisconnectPacket?
        } else {
            // Primary sub-client controls the connection
            this.peer.close(reason);
        }
    }

    protected void onClose() {
        if (!this.closed.compareAndSet(false, true)) {
            return;
        }

        if (this.packetHandler != null) try {
            this.packetHandler.onDisconnect(this.disconnectReason);
        } catch (Exception e) {
            log.error("Exception thrown while handling disconnect", e);
        }
        this.peer.removeSession(this);
    }

    protected void onPacket(BedrockPacketWrapper wrapper) {
        BedrockPacket packet = wrapper.getPacket();
        this.logInbound(packet);

        if (packetHandler == null) {
            if (log.isDebugEnabled()) {
                log.debug("Received packet without a packet handler for {}:{}: {}", this.getSocketAddress(), this.subClientId, packet);
            }
        } else if (this.packetHandler.handlePacket(packet) == PacketSignal.UNHANDLED) {
            if (log.isDebugEnabled()) {
                log.debug("Unhandled packet for {}:{}: {}", this.getSocketAddress(), this.subClientId, packet);   
            }
        }
    }

    protected void logOutbound(BedrockPacket packet) {
        if (log.isTraceEnabled() && this.logging) {
            log.trace("Outbound {}{}: {}", this.getSocketAddress(), this.subClientId, packet);
        }
    }

    protected void logInbound(BedrockPacket packet) {
        if (log.isTraceEnabled() && this.logging) {
            log.trace("Inbound {}{}: {}", this.getSocketAddress(), this.subClientId, packet);
        }
    }

    public SocketAddress getSocketAddress() {
        return peer.getSocketAddress();
    }

    public boolean isSubClient() {
        return this.subClientId != 0;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public final void disconnect() {
        disconnect("disconnect.disconnected");
    }

    public final void disconnect(String reason) {
        this.disconnect(reason, false);
    }

    public abstract void disconnect(String reason, boolean hideReason);

    public boolean isConnected() {
        return !this.closed.get();
    }
}
