package org.cloudburstmc.protocol.bedrock.netty.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.cloudburstmc.protocol.bedrock.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

public abstract class BedrockChannelInitializer extends ChannelInitializer<Channel> {


    @Override
    protected final void initChannel(Channel channel) throws Exception {

    }

    public void initPeer(BedrockPeer peer) {
    }

    public void initSession(BedrockSession session) {
    }
}
