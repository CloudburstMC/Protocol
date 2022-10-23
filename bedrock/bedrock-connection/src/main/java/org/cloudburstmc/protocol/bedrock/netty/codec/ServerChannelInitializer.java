package org.cloudburstmc.protocol.bedrock.netty.codec;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.cloudburstmc.protocol.bedrock.BedrockServer;

public class ServerChannelInitializer extends ChannelInitializer<Channel> {

    private final BedrockServer server;

    public ServerChannelInitializer(BedrockServer server) {
        this.server = server;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(ServerInboundHandler.NAME, new ServerInboundHandler(this.server));
        pipeline.addLast(ServerTailHandler.NAME, new ServerTailHandler(this.server));
    }
}
