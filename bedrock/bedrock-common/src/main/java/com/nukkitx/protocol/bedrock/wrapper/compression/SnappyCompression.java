package com.nukkitx.protocol.bedrock.wrapper.compression;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.compression.Snappy;

import java.util.zip.DataFormatException;

public class SnappyCompression implements CompressionSerializer {
    public static final SnappyCompression INSTANCE = new SnappyCompression();

    private static final ThreadLocal<Snappy> SNAPPY = ThreadLocal.withInitial(Snappy::new);

    @Override
    public void compress(ByteBuf input, ByteBuf output, int level) throws DataFormatException {
        Snappy snappy = SNAPPY.get();
        try {
            snappy.encode(input, output, input.readableBytes());
        } finally {
            snappy.reset();
        }
    }

    @Override
    public void decompress(ByteBuf input, ByteBuf output, int maxSize) throws DataFormatException {
        Snappy snappy = SNAPPY.get();
        try {
            snappy.decode(input, output);
        } finally {
            snappy.reset();
        }
    }
}
