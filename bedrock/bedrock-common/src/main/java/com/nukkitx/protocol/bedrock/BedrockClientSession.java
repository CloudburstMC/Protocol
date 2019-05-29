package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;

public class BedrockClientSession extends BedrockSession {

    BedrockClientSession(RakNetSession connection) {
        super(connection);
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.connection.disconnect();
    }
}
