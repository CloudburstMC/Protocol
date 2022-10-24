package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.cloudburstmc.protocol.bedrock.BedrockServer;

public class ServerInboundHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public final static String NAME = "bedrock-server-inbound-handler";
    private final BedrockServer server;

    public ServerInboundHandler(BedrockServer server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        if (!this.server.isBlocked(packet.sender().getAddress())) {
            // Forward only packets from allowed senders
            ctx.fireChannelRead(packet.retain());
        }
    }
}
