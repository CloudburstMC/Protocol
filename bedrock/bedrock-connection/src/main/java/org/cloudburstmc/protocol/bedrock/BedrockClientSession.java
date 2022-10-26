package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;

public class BedrockClientSession extends BedrockSession {

    BedrockClientSession(BedrockPeer peer) {
        super(peer, 0);
    }

    @Override
    public void disconnect() {
        this.checkForClosed();
        this.peer.getRakSessionCodec().disconnect(RakDisconnectReason.DISCONNECTED);
    }
}
