package org.cloudburstmc.protocol.bedrock.netty.initializer;

import io.netty.channel.Channel;
import org.cloudburstmc.protocol.bedrock.BedrockClientSession;
import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.PacketDirection;

public abstract class BedrockClientInitializer extends BedrockChannelInitializer<BedrockClientSession> {

    @Override
    protected void preInitChannel(Channel channel) throws Exception {
        channel.attr(PacketDirection.ATTRIBUTE).set(PacketDirection.SERVER_BOUND);
        super.preInitChannel(channel);
    }

    @Override
    public BedrockClientSession createSession0(BedrockPeer peer, int subClientId) {
        return new BedrockClientSession(peer, subClientId);
    }
}
