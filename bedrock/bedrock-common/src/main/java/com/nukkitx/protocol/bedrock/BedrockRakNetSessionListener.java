package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.EncapsulatedPacket;
import com.nukkitx.network.raknet.RakNetSession;
import com.nukkitx.network.raknet.RakNetSessionListener;
import com.nukkitx.network.raknet.RakNetState;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.bedrock.exception.ConnectionFailedException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@ParametersAreNonnullByDefault
public abstract class BedrockRakNetSessionListener implements RakNetSessionListener {
    final BedrockSession session;
    private final RakNetSession connection;

    @Override
    public void onEncapsulated(EncapsulatedPacket packet) {
        if (connection.getState() != RakNetState.CONNECTED) {
            // We shouldn't be receiving packets till the connection is full established.
            return;
        }
        ByteBuf buffer = packet.getBuffer();

        int packetId = buffer.readUnsignedByte();
        if (packetId == 0xfe && buffer.isReadable()) {
            // Wrapper packet
            session.onWrappedPacket(buffer);
        }
    }

    @Override
    public void onDirect(ByteBuf buf) {
        // We shouldn't be receiving direct datagram messages from the client whilst they are connected.
    }

    @ParametersAreNonnullByDefault
    public static class Client extends BedrockRakNetSessionListener {
        CompletableFuture<BedrockClientSession> future;
        private final BedrockClient client;

        Client(BedrockClientSession session, RakNetSession connection, BedrockClient client,
               CompletableFuture<BedrockClientSession> future) {
            super(session, connection);
            this.client = client;
            this.future = future;
        }

        @Override
        public void onSessionChangeState(RakNetState state) {
            if (state == RakNetState.CONNECTED && this.future != null) {
                this.future.complete((BedrockClientSession) this.session);
                this.future = null;
            }
        }

        @Override
        public void onDisconnect(DisconnectReason reason) {
            session.close(reason);
            if (this.future != null && !this.future.isDone()) {
                this.future.completeExceptionally(new ConnectionFailedException(reason));
            }
            this.client.session = null;
        }
    }

    @ParametersAreNonnullByDefault
    public static class Server extends BedrockRakNetSessionListener {
        private final BedrockServer server;

        Server(BedrockServerSession session, RakNetSession connection, BedrockServer server) {
            super(session, connection);
            this.server = server;
        }

        @Override
        public void onSessionChangeState(RakNetState state) {
            if (state == RakNetState.CONNECTED) {
                this.server.sessions.add((BedrockServerSession) this.session);
                BedrockServerEventHandler handler = this.server.getHandler();
                if (handler != null) {
                    handler.onSessionCreation((BedrockServerSession) this.session);
                }
            }
        }

        @Override
        public void onDisconnect(DisconnectReason reason) {
            this.session.close(reason);
            this.server.sessions.remove((BedrockServerSession) this.session);
        }
    }
}
