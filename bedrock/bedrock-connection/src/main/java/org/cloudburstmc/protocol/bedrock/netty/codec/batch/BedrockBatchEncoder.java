package org.cloudburstmc.protocol.bedrock.netty.codec.batch;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.cloudburstmc.protocol.bedrock.netty.BatchedMessage;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

public class BedrockBatchEncoder extends MessageToMessageEncoder<BatchedMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, BatchedMessage msg, List<Object> out) {
        List<ByteBuf> messages = msg.getMessages();
        if (messages.isEmpty()) {
            return;
        }

        CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(messages.size() * 2);
        try {
            for (ByteBuf message : messages) {
                ByteBuf header = ctx.alloc().ioBuffer(5);
                VarInts.writeUnsignedInt(buf, message.readableBytes());
                buf.addComponent(true, header);
                buf.addComponent(true, message.retain());
            }
            out.add(buf.retain());
        } finally {
            buf.release();
        }
    }
}
