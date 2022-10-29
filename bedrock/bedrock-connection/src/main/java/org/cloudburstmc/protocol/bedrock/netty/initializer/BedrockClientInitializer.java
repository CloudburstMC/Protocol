package org.cloudburstmc.protocol.bedrock.netty.initializer;

import org.cloudburstmc.protocol.bedrock.BedrockClientSession;
import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

public abstract class BedrockClientInitializer extends BedrockChannelInitializer<BedrockClientSession> {

    @Override
    public BedrockClientSession createSession0(BedrockPeer peer, int subClientId) {
        return new BedrockClientSession(peer, subClientId);
    }
}
