package org.cloudburstmc.protocol.bedrock.netty.codec.compression;

import io.airlift.compress.snappy.SnappyRawCompressor;
import io.airlift.compress.snappy.SnappyRawDecompressor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;

public class SnappyCompression implements BatchCompression {
    private static final ThreadLocal<short[]> TABLE = ThreadLocal.withInitial(() -> new short[16384]);

    @Override
    public ByteBuf encode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
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
            return output.retain();
        } finally {
            output.release();
            if (direct != msg) {
                direct.release();
            }
        }
    }

    @Override
    public ByteBuf decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
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
            return output.retain();
        } finally {
            output.release();
            if (direct != msg) {
                direct.release();
            }
        }
    }

    @Override
    public CompressionAlgorithm getAlgorithm() {
        return PacketCompressionAlgorithm.SNAPPY;
    }

    @Override
    public void setLevel(int level) {
    }

    @Override
    public int getLevel() {
        return -1;
    }
}
