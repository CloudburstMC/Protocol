package com.nukkitx.protocol.bedrock.wrapper.compression;

import io.netty.buffer.ByteBuf;

import java.util.zip.DataFormatException;

public class NoCompression implements CompressionSerializer {
    public static final NoCompression INSTANCE = new NoCompression();

    @Override
    public void compress(ByteBuf input, ByteBuf output, int level) throws DataFormatException {
        output.writeBytes(input, input.readableBytes());
    }

    @Override
    public void decompress(ByteBuf input, ByteBuf output, int maxSize) throws DataFormatException {
        output.writeBytes(input, input.readableBytes());
    }
}
