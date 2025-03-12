package org.cloudburstmc.protocol.bedrock.codec.v786.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class LevelSoundEventSerializer_v786 extends LevelSoundEventSerializer_v332 {

    public LevelSoundEventSerializer_v786(TypeMap<SoundEvent> typeMap) {
        super(typeMap);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeLongLE(packet.getActorID());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelSoundEventPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setActorID(buffer.readLongLE());
    }
}