package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.cloudburstmc.protocol.bedrock.BedrockClient;

public class ClientPeerInitializer extends ChannelInboundHandlerAdapter {

    public final static String NAME = "bedrock-client-peer-initializer";
    private final BedrockClient client;

    public ClientPeerInitializer(BedrockClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO: create bedrockPeer
    }
}
