package org.cloudburstmc.protocol.bedrock.packet;

import org.cloudburstmc.protocol.common.PacketSignal;

public class LevelSoundEventPacket extends LevelSoundEvent2Packet {

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_SOUND_EVENT;
    }

    @Override
    public LevelSoundEventPacket clone() {
        return (LevelSoundEventPacket) super.clone();
    }
}
