package org.cloudburstmc.protocol.bedrock;

@FunctionalInterface
public interface BedrockSessionFactory {

    BedrockSession createSession(BedrockPeer peer, int subClientId);
}
