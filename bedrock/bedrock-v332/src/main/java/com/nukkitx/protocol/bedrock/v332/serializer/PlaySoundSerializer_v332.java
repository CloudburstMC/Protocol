package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaySoundSerializer_v332 implements PacketSerializer<PlaySoundPacket> {
    public static final PlaySoundSerializer_v332 INSTANCE = new PlaySoundSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, PlaySoundPacket packet) {
        BedrockUtils.writeString(buffer, packet.getSound());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition().mul(3).toInt());
        buffer.writeFloatLE(packet.getVolume());
        buffer.writeFloatLE(packet.getPitch());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlaySoundPacket packet) {
        packet.setSound(BedrockUtils.readString(buffer));
        packet.setPosition(BedrockUtils.readBlockPosition(buffer).toFloat().div(8));
        packet.setVolume(buffer.readFloatLE());
        packet.setPitch(buffer.readFloatLE());
    }
}
