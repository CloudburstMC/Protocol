package org.cloudburstmc.protocol.bedrock;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;

import javax.annotation.Nonnull;
import java.net.SocketAddress;

public class BedrockSession {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockSession.class);

    private final BedrockPeer peer;
    private final int subClientId;
    private BedrockPacketHandler packetHandler;
    private boolean logging;

    public BedrockSession(BedrockPeer peer, int subClientId) {
        this.peer = peer;
        this.subClientId = subClientId;
    }

    public void setPacketHandler(@Nonnull BedrockPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    void checkForClosed() {
        if (this.closed) {
            throw new IllegalStateException("Connection has been closed");
        }
    }

    public void sendPacket(@Nonnull BedrockPacket packet) {
        this.peer.sendPacket(this.subClientId, 0, packet);
        this.logOutbound(packet);
    }

    public void sendPacketImmediately(@Nonnull BedrockPacket packet) {
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

    public void close(RakDisconnectReason reason) {
        checkForClosed();
    }

    protected void onPacket(BedrockPacket packet) {
        this.logInbound(packet);
        if (packetHandler == null) {
            log.warn("Received packet without a packet handler for {}:{}: {}", this.getSocketAddress(), this.subClientId, packet);
        }
        this.packetHandler.handlePacket(packet);
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
}
