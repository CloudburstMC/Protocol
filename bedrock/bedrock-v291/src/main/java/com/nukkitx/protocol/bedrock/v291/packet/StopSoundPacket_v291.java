package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.StopSoundPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class StopSoundPacket_v291 extends StopSoundPacket {
    private String soundName;
    private boolean stoppingAllSound;

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, soundName);
        buffer.writeBoolean(stoppingAllSound);
    }

    @Override
    public void decode(ByteBuf buffer) {
        soundName = BedrockUtils.readString(buffer);
        stoppingAllSound = buffer.readBoolean();
    }
}
