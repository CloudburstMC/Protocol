package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent2Packet;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.v361.serializer.LevelSoundEvent1Serializer_v361.SOUNDS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelSoundEvent2Serializer_v361 implements PacketSerializer<LevelSoundEvent2Packet> {
    public static final LevelSoundEvent2Serializer_v361 INSTANCE = new LevelSoundEvent2Serializer_v361();

    @Override
    public void serialize(ByteBuf buffer, LevelSoundEvent2Packet packet) {
        buffer.writeByte(SOUNDS.get(packet.getSound()));
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        BedrockUtils.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelSoundEvent2Packet packet) {
        packet.setSound(SOUNDS.get(buffer.readUnsignedByte()));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(BedrockUtils.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}
