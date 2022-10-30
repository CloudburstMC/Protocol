package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class SnappyCompressionCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> implements CompressionCodec {

    public static final String NAME = "compression-codec";

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // TODO: Implement
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // TODO: Implement
    }

    @Override
    public int getLevel() {
        return -1;
    }

    @Override
    public void setLevel(int level) {
        // no-op
    }
}
