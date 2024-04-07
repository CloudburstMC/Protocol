package org.cloudburstmc.protocol.bedrock.netty.initializer;

import io.netty.channel.Channel;
import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.PacketDirection;

public abstract class BedrockServerInitializer extends BedrockChannelInitializer<BedrockServerSession> {

    @Override
    protected void preInitChannel(Channel channel) throws Exception {
        channel.attr(PacketDirection.ATTRIBUTE).set(PacketDirection.CLIENT_BOUND);
        super.preInitChannel(channel);
    }

    @Override
    public BedrockServerSession createSession0(BedrockPeer peer, int subClientId) {
        return new BedrockServerSession(peer, subClientId);
    }
}
