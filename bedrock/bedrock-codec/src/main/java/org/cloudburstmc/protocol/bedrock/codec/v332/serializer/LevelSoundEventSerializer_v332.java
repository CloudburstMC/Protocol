package org.cloudburstmc.protocol.bedrock.codec.v332.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class LevelSoundEventSerializer_v332 implements BedrockPacketSerializer<LevelSoundEventPacket> {
    private final TypeMap<SoundEvent> typeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        VarInts.writeUnsignedInt(buffer, typeMap.getId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        helper.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        packet.setSound(typeMap.getType(VarInts.readUnsignedInt(buffer)));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}

