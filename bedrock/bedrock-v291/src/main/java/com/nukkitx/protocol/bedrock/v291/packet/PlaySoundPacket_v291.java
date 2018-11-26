package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class PlaySoundPacket_v291 extends PlaySoundPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, sound);
        BedrockUtils.writeBlockPosition(buffer, position.mul(3).toInt());
        buffer.writeFloatLE(volume);
        buffer.writeFloatLE(pitch);
    }

    @Override
    public void decode(ByteBuf buffer) {
        sound = BedrockUtils.readString(buffer);
        position = BedrockUtils.readBlockPosition(buffer).toFloat().div(8);
        volume = buffer.readFloatLE();
        pitch = buffer.readFloatLE();
    }
}
