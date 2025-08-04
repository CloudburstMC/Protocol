package org.cloudburstmc.protocol.bedrock;

import net.kyori.adventure.text.Component;

public class BedrockClientSession extends BedrockSession {

    public BedrockClientSession(BedrockPeer peer, int subClientId) {
        super(peer, subClientId);
    }

    @Override
    public void disconnect(Component reason, boolean hideReason) {
        this.close(reason);
    }
}
