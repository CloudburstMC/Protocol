package org.cloudburstmc.protocol.bedrock;

public class BedrockClientSession extends BedrockSession {

    public BedrockClientSession(BedrockPeer peer, int subClientId) {
        super(peer, subClientId);
    }

    @Override
    public void disconnect(String reason, boolean hideReason) {
        this.close(reason);
    }
}
