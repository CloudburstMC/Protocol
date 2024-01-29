package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;

import java.util.List;

public class CompressionCodec extends MessageToMessageCodec<BedrockBatchWrapper, BedrockBatchWrapper> {
    public static final String NAME = "compression-codec";

    private final CompressionStrategy strategy;
    private final boolean prefixed;

    public CompressionCodec(CompressionStrategy strategy, boolean prefixed) {
        this.strategy = strategy;
        this.prefixed = prefixed;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) throws Exception {
        if (msg.getCompressed() == null && msg.getUncompressed() == null) {
            throw new IllegalStateException("Batch was not encoded before");
        }

        if (msg.getCompressed() != null && !msg.isModified()) {
            out.add(msg.retain());
            return;
        }

        BatchCompression compression = this.strategy.getCompression(msg);
        if (!this.prefixed && this.strategy.getDefaultCompression().getAlgorithm() != compression.getAlgorithm()) {
            throw new IllegalStateException("Non-default compression algorithm used without prefixing");
        }

        ByteBuf compressed = compression.encode(ctx, msg.getUncompressed());
        try {
            ByteBuf outBuf;
            if (this.prefixed) {
                // Do not use a composite buffer as encryption does not like it
                outBuf = ctx.alloc().ioBuffer(1 + compressed.readableBytes());
                outBuf.writeByte(this.getCompressionHeader(compression.getAlgorithm()));
                outBuf.writeBytes(compressed);
            } else {
                outBuf = compressed.retain();
            }

            msg.setCompressed(outBuf, compression.getAlgorithm());
        } finally {
            compressed.release();
        }

        out.add(msg.retain());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) throws Exception {
        ByteBuf compressed = msg.getCompressed().slice();

        BatchCompression compression;
        if (this.prefixed) {
            PacketCompressionAlgorithm algorithm = this.getCompressionAlgorithm(compressed.readByte());
            compression = this.strategy.getCompression(algorithm);
        } else {
            compression = this.strategy.getDefaultCompression();
        }

        msg.setAlgorithm(compression.getAlgorithm());
        msg.setUncompressed(compression.decode(ctx, compressed.slice()));
        out.add(msg.retain());
    }

    // TODO: consider moving to strategy
    private byte getCompressionHeader(CompressionAlgorithm algorithm) {
        if (algorithm.equals(PacketCompressionAlgorithm.NONE)) {
            return (byte) 0xff;
        } else if (algorithm.equals(PacketCompressionAlgorithm.ZLIB)) {
            return 0x00;
        } else if (algorithm.equals(PacketCompressionAlgorithm.SNAPPY)) {
            return 0x01;
        }
        throw new IllegalArgumentException("Unknown compression algorithm " + algorithm);
    }

    // TODO: consider moving to strategy
    protected PacketCompressionAlgorithm getCompressionAlgorithm(byte header) {
        switch (header) {
            case 0x00:
                return PacketCompressionAlgorithm.ZLIB;
            case 0x01:
                return PacketCompressionAlgorithm.SNAPPY;
            case (byte) 0xff:
                return PacketCompressionAlgorithm.NONE;
        }
        throw new IllegalArgumentException("Unknown compression algorithm " + header);
    }

    public CompressionStrategy getStrategy() {
        return this.strategy;
    }
}
