package com.nukkitx.protocol.bedrock.wrapper.compression;

import io.airlift.compress.IncompatibleJvmException;
import io.airlift.compress.snappy.SnappyCompressor;
import io.airlift.compress.snappy.SnappyDecompressor;
import io.airlift.compress.snappy.SnappyRawDecompressor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.handler.codec.compression.Snappy;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.DataFormatException;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;

public class SnappyCompression implements CompressionSerializer {
    public static final SnappyCompression INSTANCE = new SnappyCompression();

    public static final Unsafe UNSAFE;
    private static final long ADDRESS_OFFSET;

    static {
        ByteOrder order = ByteOrder.nativeOrder();
        if (!order.equals(ByteOrder.LITTLE_ENDIAN)) {
            throw new IncompatibleJvmException(String.format("Snappy requires a little endian platform (found %s)", order));
        }

        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new IncompatibleJvmException("Snappy requires access to sun.misc.Unsafe");
        }

        try {
            // fetch the address field for direct buffers
            ADDRESS_OFFSET = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        } catch (NoSuchFieldException e) {
            throw new IncompatibleJvmException("Snappy requires access to java.nio.Buffer raw address field");
        }
    }

    private static final ThreadLocal<SnappyCompressor> COMPRESSOR = ThreadLocal.withInitial(SnappyCompressor::new);
    private static final ThreadLocal<SnappyDecompressor> DECOMPRESSOR = ThreadLocal.withInitial(SnappyDecompressor::new);

    @Override
    public void compress(ByteBuf input, ByteBuf output, int level) throws DataFormatException {
        ByteBuf direct;
        if (!input.isDirect() || input instanceof CompositeByteBuf) {
            direct = ByteBufAllocator.DEFAULT.ioBuffer();
            direct.writeBytes(input);
        } else {
            direct = input;
        }

        try {
            SnappyCompressor compressor = COMPRESSOR.get();
            output.ensureWritable(compressor.maxCompressedLength(direct.readableBytes()));

            ByteBuffer inputBuffer = direct.internalNioBuffer(direct.readerIndex(), direct.readableBytes());
            ByteBuffer outputBuffer = output.internalNioBuffer(output.writerIndex(), output.writableBytes());

            int index = outputBuffer.position();
            compressor.compress(inputBuffer, outputBuffer);
            output.writerIndex(output.writerIndex() + (outputBuffer.position() - index));
        } finally {
            if (direct != input) {
                direct.release();
            }
        }
    }

    @Override
    public void decompress(ByteBuf input, ByteBuf output, int maxSize) throws DataFormatException {
        ByteBuf direct;
        if (!input.isDirect() || input instanceof CompositeByteBuf) {
            direct = ByteBufAllocator.DEFAULT.ioBuffer();
            direct.writeBytes(input);
        } else {
            direct = input;
        }

        try {
            ByteBuffer inputBuffer = direct.internalNioBuffer(direct.readerIndex(), direct.readableBytes());
            output.ensureWritable(maxUncompressedSize(inputBuffer));
            ByteBuffer outputBuffer = output.internalNioBuffer(output.writerIndex(), output.writableBytes());

            SnappyDecompressor decompressor = DECOMPRESSOR.get();

            int index = outputBuffer.position();
            decompressor.decompress(inputBuffer, outputBuffer);
            output.writerIndex(output.writerIndex() + (outputBuffer.position() - index));
        } finally {
            if (direct != input) {
                direct.release();
            }
        }
    }

    private int maxUncompressedSize(ByteBuffer buffer) {
        Object inputBase;
        long arrayAddress;
        long limit;
        if (buffer.isDirect()) {
            inputBase = null;
            long address = UNSAFE.getLong(buffer, ADDRESS_OFFSET);
            arrayAddress = address + buffer.position();
            limit = address + buffer.limit();
        } else if (buffer.hasArray()) {
            inputBase = buffer.array();
            arrayAddress = ARRAY_BYTE_BASE_OFFSET + buffer.arrayOffset() + buffer.position();
            limit = ARRAY_BYTE_BASE_OFFSET + buffer.arrayOffset() + buffer.limit();
        } else {
            throw new IllegalStateException("Unsupported ByteBuffer " + buffer.getClass().getName());
        }
        return SnappyRawDecompressor.getUncompressedLength(inputBase, arrayAddress, limit);
    }
}
