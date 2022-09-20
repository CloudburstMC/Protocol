package com.nukkitx.protocol.bedrock.wrapper.compression;

import io.netty.buffer.ByteBuf;

import java.util.zip.DataFormatException;

public interface CompressionSerializer {

    void compress(ByteBuf input, ByteBuf output, int level) throws DataFormatException;

    void decompress(ByteBuf input, ByteBuf output, int maxSize) throws DataFormatException;
}
