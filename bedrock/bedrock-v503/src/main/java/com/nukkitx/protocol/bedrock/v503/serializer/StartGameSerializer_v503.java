package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.protocol.bedrock.v475.serializer.StartGameSerializer_v475;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v503 extends StartGameSerializer_v475 {
    public static final StartGameSerializer_v503 INSTANCE = new StartGameSerializer_v503();

    @Override
    protected long readSeed(ByteBuf buf) {
        return buf.readLongLE();
    }

    @Override
    protected void writeSeed(ByteBuf buf, long seed) {
        buf.writeLongLE(seed);
    }
}
