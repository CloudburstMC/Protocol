package com.nukkitx.protocol.bedrock.wrapper.compression;

import com.nukkitx.protocol.util.Zlib;
import io.netty.buffer.ByteBuf;

import java.util.zip.DataFormatException;

public class ZlibCompression implements CompressionSerializer {
    public static final ZlibCompression INSTANCE = new ZlibCompression();

    @Override
    public void compress(ByteBuf input, ByteBuf output, int level) throws DataFormatException {
        Zlib.RAW.deflate(input, output, level);
    }

    @Override
    public void decompress(ByteBuf input, ByteBuf output, int maxSize) throws DataFormatException {
        Zlib.RAW.inflate(input, output, maxSize);
    }
}
