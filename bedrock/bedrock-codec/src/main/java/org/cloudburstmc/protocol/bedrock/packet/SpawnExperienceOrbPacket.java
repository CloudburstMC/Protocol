package org.cloudburstmc.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SpawnExperienceOrbPacket implements BedrockPacket {
    private Vector3f position;
    private int amount;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SPAWN_EXPERIENCE_ORB;
    }
}
