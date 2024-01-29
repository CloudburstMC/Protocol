package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;

public interface BatchCompression {

    ByteBuf encode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception;
    ByteBuf decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception;

    CompressionAlgorithm getAlgorithm();

    void setLevel(int level);

    int getLevel();
}
