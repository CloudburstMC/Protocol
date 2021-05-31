package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelSoundEventSerializer_v407 implements BedrockPacketSerializer<LevelSoundEventPacket> {
    public static final LevelSoundEventSerializer_v407 INSTANCE = new LevelSoundEventSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        VarInts.writeUnsignedInt(buffer, helper.getSoundEventId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        helper.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        packet.setSound(helper.getSoundEvent(VarInts.readUnsignedInt(buffer)));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}

