package com.nukkitx.protocol.bedrock.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class LittleEndianByteBufOutputStream extends ByteBufOutputStream {

    public LittleEndianByteBufOutputStream(ByteBuf buffer) {
        super(buffer);
    }

    @Override
    public void writeShort(int val) throws IOException {
        super.writeShort(Short.reverseBytes((short) val));
    }

    @Override
    public void writeLong(long val) throws IOException {
        super.writeLong(Long.reverseBytes(val));
    }

    @Override
    public void writeInt(int val) throws IOException {
        super.writeInt(Integer.reverseBytes(val));
    }
}
