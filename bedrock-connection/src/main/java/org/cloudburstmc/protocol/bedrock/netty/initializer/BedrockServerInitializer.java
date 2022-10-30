package org.cloudburstmc.protocol.bedrock.netty.initializer;

import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;

public abstract class BedrockServerInitializer extends BedrockChannelInitializer<BedrockServerSession> {

    @Override
    public BedrockServerSession createSession0(BedrockPeer peer, int subClientId) {
        return new BedrockServerSession(peer, subClientId);
    }
}
