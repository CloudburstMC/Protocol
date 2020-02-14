package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.v388.serializer.LevelSoundEvent1Serializer_v388.SOUNDS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelSoundEvent3Serializer_v388 implements PacketSerializer<LevelSoundEventPacket> {
    public static final LevelSoundEvent3Serializer_v388 INSTANCE = new LevelSoundEvent3Serializer_v388();

    @Override
    public void serialize(ByteBuf buffer, LevelSoundEventPacket packet) {
        VarInts.writeUnsignedInt(buffer, SOUNDS.get(packet.getSound()));
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        BedrockUtils.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelSoundEventPacket packet) {
        packet.setSound(SOUNDS.get(VarInts.readUnsignedInt(buffer)));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(BedrockUtils.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}

