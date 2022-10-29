package org.cloudburstmc.protocol.bedrock.netty.initializer;

import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

public abstract class BedrockServerInitializer extends BedrockChannelInitializer {
    @Override
    public BedrockSession createSession0(BedrockPeer peer, int subClientId) {
        return new BedrockServerSession(peer, subClientId);
    }
}
