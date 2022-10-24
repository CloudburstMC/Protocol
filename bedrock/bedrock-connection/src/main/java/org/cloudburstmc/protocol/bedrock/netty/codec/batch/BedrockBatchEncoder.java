package org.cloudburstmc.protocol.bedrock.netty.codec.batch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Iterator;
import java.util.List;

public class BedrockBatchEncoder extends ChannelOutboundHandlerAdapter {

    public static final String NAME = "bedrock-batch-encoder";

    private final List<ByteBuf> messages = new ObjectArrayList<>();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            super.write(ctx, msg, promise);
            return;
        }

        // Accumulate messages to batch
        messages.add((ByteBuf) msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (messages.isEmpty()) {
            super.flush(ctx);
            return;
        }

        CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(messages.size() * 2);
        try {
            for (ByteBuf message : messages) {
                ByteBuf header = ctx.alloc().ioBuffer(5);
                org.cloudburstmc.protocol.common.util.VarInts.writeUnsignedInt(buf, message.readableBytes());
                buf.addComponent(true, header);
                buf.addComponent(true, message.retain());
            }
            ctx.write(buf.retain());
        } finally {
            buf.release();
        }

        super.flush(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Iterator<ByteBuf> iterator = messages.iterator();
        while (iterator.hasNext()) {
            iterator.next().release();
            iterator.remove();
        }
        super.handlerRemoved(ctx);
    }
}
