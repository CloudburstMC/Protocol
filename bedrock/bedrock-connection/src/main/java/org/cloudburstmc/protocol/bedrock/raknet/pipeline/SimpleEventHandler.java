package org.cloudburstmc.protocol.bedrock.raknet.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleUserEventChannelHandler;

import java.util.function.BiConsumer;

public class SimpleEventHandler<T> extends SimpleUserEventChannelHandler<T> {

    private final BiConsumer<ChannelHandlerContext, T> callback;

    public SimpleEventHandler(BiConsumer<ChannelHandlerContext, T> callback) {
        this.callback = callback;
    }

    @Override
    protected void eventReceived(ChannelHandlerContext ctx, T t) throws Exception {
        this.callback.accept(ctx, t);
    }
}
