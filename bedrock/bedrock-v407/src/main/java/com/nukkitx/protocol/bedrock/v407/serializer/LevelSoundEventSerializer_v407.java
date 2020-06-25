package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelSoundEventSerializer_v407 implements BedrockPacketSerializer<LevelSoundEventPacket> {
    public static final LevelSoundEventSerializer_v407 INSTANCE = new LevelSoundEventSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
        VarInts.writeUnsignedInt(buffer, helper.getSoundEventId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        helper.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelSoundEventPacket packet) {
        packet.setSound(helper.getSoundEvent(VarInts.readUnsignedInt(buffer)));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}

