package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.protocol.bedrock.raknet.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializer;

public class BedrockClientSession extends BedrockSession {

    BedrockClientSession(BedrockPeer<?> peer, BedrockWrapperSerializer serializer) {
        super(peer, serializer);
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.peer.getRakSessionCodec().disconnect(RakDisconnectReason.DISCONNECTED);
    }
}
