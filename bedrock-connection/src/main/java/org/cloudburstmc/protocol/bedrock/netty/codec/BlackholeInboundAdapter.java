package org.cloudburstmc.protocol.bedrock.netty.codec;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Sharable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlackholeInboundAdapter extends ChannelInboundHandlerAdapter {

    public static final String NAME = "blackhole-inbound-adapter";
    public static final BlackholeInboundAdapter INSTANCE = new BlackholeInboundAdapter();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ReferenceCountUtil.release(msg);
    }
}
