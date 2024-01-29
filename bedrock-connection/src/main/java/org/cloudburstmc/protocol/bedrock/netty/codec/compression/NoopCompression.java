package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;

public class NoopCompression implements BatchCompression {

    @Override
    public ByteBuf encode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        return msg.retainedSlice();
    }

    @Override
    public ByteBuf decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        return msg.retainedSlice();
    }

    @Override
    public CompressionAlgorithm getAlgorithm() {
        return null;
    }

    @Override
    public void setLevel(int level) {
    }

    @Override
    public int getLevel() {
        return -1;
    }
}
