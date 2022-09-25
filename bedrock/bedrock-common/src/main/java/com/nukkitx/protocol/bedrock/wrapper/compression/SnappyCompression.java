package com.nukkitx.protocol.bedrock.wrapper.compression;

import io.airlift.compress.snappy.SnappyRawCompressor;
import io.airlift.compress.snappy.SnappyRawDecompressor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;

public class SnappyCompression implements CompressionSerializer {
    public static final SnappyCompression INSTANCE = new SnappyCompression();

    private static final ThreadLocal<short[]> TABLE = ThreadLocal.withInitial(() -> new short[16384]);

    @Override
    public void compress(ByteBuf input, ByteBuf output, int level) {
        ByteBuf direct;
        if (!input.isDirect() || input instanceof CompositeByteBuf) {
            direct = ByteBufAllocator.DEFAULT.ioBuffer(input.readableBytes());
            direct.writeBytes(input);
        } else {
            direct = input;
        }

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
        } finally {
            if (direct != input) {
                direct.release();
            }
        }
    }

    @Override
    public void decompress(ByteBuf input, ByteBuf output, int maxSize) {
        ByteBuf direct;
        if (!input.isDirect() || input instanceof CompositeByteBuf) {
            direct = ByteBufAllocator.DEFAULT.ioBuffer(input.readableBytes());
            direct.writeBytes(input);
        } else {
            direct = input;
        }

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
        } finally {
            if (direct != input) {
                direct.release();
            }
        }
    }
}
