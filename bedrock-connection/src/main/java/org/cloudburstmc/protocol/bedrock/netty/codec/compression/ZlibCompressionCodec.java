package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.common.util.Zlib;

import java.util.List;

@RequiredArgsConstructor
public class ZlibCompressionCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> implements CompressionCodec {

    public static final String NAME = "compression-codec";

    private static final int MAX_DECOMPRESSED_BYTES = Integer.getInteger("bedrock.maxDecompressedBytes", 1024 * 1024 * 10);

    private final Zlib zlib;
    private int level = 7;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf outBuf = ctx.alloc().ioBuffer(msg.readableBytes() << 3);
        try {
            zlib.deflate(msg, outBuf, level);
            out.add(outBuf.retain());
        } finally {
            outBuf.release();
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(zlib.inflate(msg, MAX_DECOMPRESSED_BYTES));
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public PacketCompressionAlgorithm getAlgorithm() {
        return PacketCompressionAlgorithm.ZLIB;
    }
}
