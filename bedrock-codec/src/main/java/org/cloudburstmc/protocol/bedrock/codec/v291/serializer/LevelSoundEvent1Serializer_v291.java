package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent1Packet;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class LevelSoundEvent1Serializer_v291 implements BedrockPacketSerializer<LevelSoundEvent1Packet> {

    private final TypeMap<SoundEvent> soundTypeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEvent1Packet packet) {
        buffer.writeByte(soundTypeMap.getId(packet.getSound()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getExtraData());
        VarInts.writeInt(buffer, packet.getPitch());
        buffer.writeBoolean(packet.isBabySound());
        buffer.writeBoolean(packet.isRelativeVolumeDisabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEvent1Packet packet) {
        packet.setSound(soundTypeMap.getType(buffer.readUnsignedByte()));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setExtraData(VarInts.readInt(buffer));
        packet.setPitch(VarInts.readInt(buffer));
        packet.setBabySound(buffer.readBoolean());
        packet.setRelativeVolumeDisabled(buffer.readBoolean());
    }
}
