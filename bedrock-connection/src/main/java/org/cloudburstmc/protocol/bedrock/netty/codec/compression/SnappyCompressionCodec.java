package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.airlift.compress.snappy.SnappyRawCompressor;
import io.airlift.compress.snappy.SnappyRawDecompressor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;

import java.util.List;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;

public class SnappyCompressionCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> implements CompressionCodec {
    public static final String NAME = "compression-codec";

    private static final ThreadLocal<short[]> TABLE = ThreadLocal.withInitial(() -> new short[16384]);

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf direct;
        if (!msg.isDirect() || msg instanceof CompositeByteBuf) {
            direct = ctx.alloc().ioBuffer(msg.readableBytes());
            direct.writeBytes(msg);
        } else {
            direct = msg;
        }

        ByteBuf output = ctx.alloc().directBuffer();
        try {
            long inputAddress = direct.memoryAddress() + direct.readerIndex();
            long inputEndAddress = inputAddress + direct.readableBytes();

            output.ensureWritable(SnappyRawCompressor.maxCompressedLength(direct.readableBytes()));

            long outputAddress;
            long outputEndAddress;
            byte[] outputArray = null;
            if (output.isDirect() && output.hasMemoryAddress()) {
                outputAddress = output.memoryAddress() + output.writerIndex();
                outputEndAddress = outputAddress + output.writableBytes();
            } else if (output.hasArray()) {
                outputArray = output.array();
                outputAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.writerIndex();
                outputEndAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.writableBytes();
            } else {
                throw new IllegalStateException("Unsupported ByteBuf " + output.getClass().getSimpleName());
            }

            int compressed = SnappyRawCompressor.compress(null, inputAddress, inputEndAddress, outputArray, outputAddress, outputEndAddress, TABLE.get());
            output.writerIndex(output.writerIndex() + compressed);
            out.add(output.retain());
        } finally {
            output.release();
            if (direct != msg) {
                direct.release();
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf direct;
        if (!msg.isDirect() || msg instanceof CompositeByteBuf) {
            direct = ctx.alloc().ioBuffer(msg.readableBytes());
            direct.writeBytes(msg);
        } else {
            direct = msg;
        }

        ByteBuf output = ctx.alloc().directBuffer();
        try {
            long inputAddress = direct.memoryAddress() + direct.readerIndex();
            long inputEndAddress = inputAddress + direct.readableBytes();
            output.ensureWritable(SnappyRawDecompressor.getUncompressedLength(null, inputAddress, inputEndAddress));

            long outputAddress;
            long outputEndAddress;
            byte[] outputArray = null;
            if (output.isDirect() && output.hasMemoryAddress()) {
                outputAddress = output.memoryAddress() + output.writerIndex();
                outputEndAddress = outputAddress + output.writableBytes();
            } else if (output.hasArray()) {
                outputArray = output.array();
                outputAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.writerIndex();
                outputEndAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.writableBytes();
            } else {
                throw new IllegalStateException("Unsupported ByteBuf " + output.getClass().getSimpleName());
            }

            int decompressed = SnappyRawDecompressor.decompress(null, inputAddress, inputEndAddress, outputArray, outputAddress, outputEndAddress);
            output.writerIndex(output.writerIndex() + decompressed);
            out.add(output.retain());
        } finally {
            output.release();
            if (direct != msg) {
                direct.release();
            }
        }
    }

    @Override
    public int getLevel() {
        return -1;
    }

    @Override
    public void setLevel(int level) {
        // no-op
    }

    @Override
    public PacketCompressionAlgorithm getAlgorithm() {
        return PacketCompressionAlgorithm.SNAPPY;
    }
}
