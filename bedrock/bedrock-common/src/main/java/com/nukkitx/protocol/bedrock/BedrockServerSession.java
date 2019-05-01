package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;
import com.nukkitx.network.raknet.RakNetState;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BedrockServerSession extends BedrockSession {
    private final BedrockServer server;

    BedrockServerSession(RakNetSession connection, BedrockServer server) {
        super(connection);
        this.server = server;
    }

    @Override
    protected void setupConnection() {
        this.connection.setListener(new ServerSessionListener());
    }

    @Override
    public void disconnect() {
        this.disconnect(null, true);
    }

    public void disconnect(@Nullable String reason) {
        this.disconnect(reason, false);
    }

    public void disconnect(@Nullable String reason, boolean hideReason) {
        this.checkForClosed();

        DisconnectPacket packet = new DisconnectPacket();
        if (reason == null || hideReason) {
            packet.setDisconnectScreenHidden(true);
            reason = "disconnect.disconnected";
        }
        packet.setKickMessage(reason);
        this.sendPacketImmediately(packet);
    }

    public BedrockServer getServer() {
        return server;
    }

    @ParametersAreNonnullByDefault
    private class ServerSessionListener extends BedrockSessionListener {

        @Override
        public void onSessionChangeState(RakNetState state) {
            if (state == RakNetState.CONNECTED) {
                BedrockServerSession.this.server.sessions.add(BedrockServerSession.this);
                BedrockServerEventHandler handler = BedrockServerSession.this.server.getHandler();
                if (handler != null) {
                    handler.onSessionCreation(BedrockServerSession.this);
                }
            }
        }

        @Override
        public void onDisconnect(DisconnectReason reason) {
            BedrockServerSession.this.close(reason);
            BedrockServerSession.this.server.sessions.remove(BedrockServerSession.this);
        }
    }
}
