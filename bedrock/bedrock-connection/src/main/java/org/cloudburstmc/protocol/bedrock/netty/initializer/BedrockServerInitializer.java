package org.cloudburstmc.protocol.bedrock.netty.initializer;

import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

public class BedrockServerInitializer extends BedrockChannelInitializer {
    @Override
    public BedrockSession createSession(BedrockPeer peer, int subClientId) {
        return new BedrockSession(peer, subClientId);
    }
}
