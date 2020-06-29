package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializer;

public class BedrockClientSession extends BedrockSession {

    BedrockClientSession(RakNetSession connection, BedrockWrapperSerializer serializer) {
        super(connection, serializer);
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.connection.disconnect();
    }
}
