package org.cloudburstmc.protocol.bedrock.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class RakNetFrameCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> {

    private static final int RAKNET_MINECRAFT_ID = 0xFE;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        CompositeByteBuf buf = ctx.alloc().compositeDirectBuffer(2);
        buf.addComponent(true, ctx.alloc().ioBuffer(1).writeByte(RAKNET_MINECRAFT_ID));
        buf.addComponent(true, msg.retain());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (!msg.isReadable()) {
            return;
        }
        int id = msg.readUnsignedByte();
        if (id != RAKNET_MINECRAFT_ID) {
            throw new IllegalStateException("Invalid RakNet Minecraft ID: " + id);
        }
        out.add(msg.readRetainedSlice(msg.readableBytes()));
    }
}
