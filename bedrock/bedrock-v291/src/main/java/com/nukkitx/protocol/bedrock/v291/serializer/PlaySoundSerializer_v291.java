package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaySoundSerializer_v291 implements BedrockPacketSerializer<PlaySoundPacket> {
    public static final PlaySoundSerializer_v291 INSTANCE = new PlaySoundSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlaySoundPacket packet) {
        helper.writeString(buffer, packet.getSound());
        helper.writeBlockPosition(buffer, packet.getPosition().mul(3).toInt());
        buffer.writeFloatLE(packet.getVolume());
        buffer.writeFloatLE(packet.getPitch());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlaySoundPacket packet) {
        packet.setSound(helper.readString(buffer));
        packet.setPosition(helper.readBlockPosition(buffer).toFloat().div(8));
        packet.setVolume(buffer.readFloatLE());
        packet.setPitch(buffer.readFloatLE());
    }
}
