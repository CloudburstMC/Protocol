package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.StopSoundPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StopSoundSerializer_v291 implements BedrockPacketSerializer<StopSoundPacket> {
    public static final StopSoundSerializer_v291 INSTANCE = new StopSoundSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StopSoundPacket packet) {
        helper.writeString(buffer, packet.getSoundName());
        buffer.writeBoolean(packet.isStoppingAllSound());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StopSoundPacket packet) {
        packet.setSoundName(helper.readString(buffer));
        packet.setStoppingAllSound(buffer.readBoolean());
    }
}
