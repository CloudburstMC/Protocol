package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3f;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class LevelSoundEvent2Packet extends BedrockPacket {
    private SoundEvent sound;
    private Vector3f position;
    private int extraData;
    private String identifier;
    private boolean babySound;
    private boolean relativeVolumeDisabled;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_SOUND_EVENT_2;
    }
}
