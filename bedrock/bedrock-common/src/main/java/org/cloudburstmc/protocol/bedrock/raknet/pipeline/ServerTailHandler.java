package org.cloudburstmc.protocol.bedrock.raknet.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.protocol.bedrock.BedrockServer;

public class ServerTailHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(ServerInboundHandler.class);
    public final static String NAME = "bedrock-server-tail-handler";
    private final BedrockServer server;

    public ServerTailHandler(BedrockServer server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("Unhandled content " + msg.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception was caught at RakNet server channel!", cause);
    }
}
