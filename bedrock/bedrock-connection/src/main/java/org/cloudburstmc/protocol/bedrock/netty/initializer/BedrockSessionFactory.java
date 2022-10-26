package org.cloudburstmc.protocol.bedrock.netty.initializer;

import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

@FunctionalInterface
public interface BedrockSessionFactory {

    BedrockSession createSession(BedrockPeer peer, int subClientId);
}
