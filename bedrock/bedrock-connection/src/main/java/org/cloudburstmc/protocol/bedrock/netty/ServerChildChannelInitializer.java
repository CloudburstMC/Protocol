package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.cloudburstmc.protocol.bedrock.BedrockServer;

public class ServerChildChannelInitializer extends ChannelInitializer<Channel> {
    private final BedrockServer server;

    public ServerChildChannelInitializer(BedrockServer server) {
        this.server = server;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        BedrockPeer<?> peer = this.server.constructBedrockPeer(channel);
        if (peer == null) {
            channel.close();
        } else {
            // TODO: adjust channel options if required
            channel.pipeline().addLast(BedrockPeer.NAME, peer);
        }
    }
}
