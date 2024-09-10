package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LevelSoundEvent1Packet implements BedrockPacket {
    private SoundEvent sound;
    private Vector3f position;
    private int extraData;
    private int pitch;
    private boolean babySound;
    private boolean relativeVolumeDisabled;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_SOUND_EVENT_1;
    }

    @Override
    public LevelSoundEvent1Packet clone() {
        try {
            return (LevelSoundEvent1Packet) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

