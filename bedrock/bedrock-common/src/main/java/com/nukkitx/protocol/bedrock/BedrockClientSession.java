package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;
import com.nukkitx.network.raknet.RakNetState;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.bedrock.exception.ConnectionFailedException;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class BedrockClientSession extends BedrockSession {
    private final BedrockClient client;
    private CompletableFuture<BedrockClientSession> future;

    BedrockClientSession(RakNetSession connection, BedrockClient client, CompletableFuture<BedrockClientSession> future) {
        super(connection);
        this.client = client;
        this.future = future;
    }

    @Override
    protected void setupConnection() {
        this.connection.setListener(new ClientSessionListener());
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.connection.disconnect();
    }

    public BedrockClient getClient() {
        return client;
    }

    @ParametersAreNonnullByDefault
    private class ClientSessionListener extends BedrockSessionListener {

        @Override
        public void onSessionChangeState(RakNetState state) {
            if (state == RakNetState.CONNECTED && BedrockClientSession.this.future != null) {
                BedrockClientSession.this.future.complete(BedrockClientSession.this);
                BedrockClientSession.this.future = null;
            }
        }

        @Override
        public void onDisconnect(DisconnectReason reason) {
            BedrockClientSession.this.close(reason);
            if (BedrockClientSession.this.future != null && !BedrockClientSession.this.future.isDone()) {
                BedrockClientSession.this.future.completeExceptionally(new ConnectionFailedException(reason));
            }
            BedrockClientSession.this.client.session = null;
        }
    }
}
