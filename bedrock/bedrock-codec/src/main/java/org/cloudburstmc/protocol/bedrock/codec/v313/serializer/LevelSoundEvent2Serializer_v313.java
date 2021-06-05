package org.cloudburstmc.protocol.bedrock.codec.v313.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent2Packet;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class LevelSoundEvent2Serializer_v313 implements BedrockPacketSerializer<LevelSoundEvent2Packet> {

    private final TypeMap<SoundEvent> soundEvents;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEvent2Packet packet) {
        buffer.writeByte(soundEvents.getId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        helper.writeString(buffer, packet.getIdentifier());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEvent2Packet packet) {
        packet.setSound(soundEvents.getType(buffer.readUnsignedByte()));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setIdentifier(helper.readString(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}
