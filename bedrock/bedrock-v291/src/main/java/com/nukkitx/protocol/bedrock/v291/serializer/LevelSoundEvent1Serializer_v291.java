package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent1Packet;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelSoundEvent1Serializer_v291 implements BedrockPacketSerializer<LevelSoundEvent1Packet> {
    public static final LevelSoundEvent1Serializer_v291 INSTANCE = new LevelSoundEvent1Serializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent1Packet packet) {
        buffer.writeByte(helper.getSoundEventId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        VarInts.writeInt(buffer, packet.getPitch());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEvent1Packet packet) {
        packet.setSound(helper.getSoundEvent(buffer.readUnsignedByte()));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setPitch(VarInts.readInt(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}
