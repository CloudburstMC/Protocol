package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ChangeDimensionPacket implements BedrockPacket {
    private int dimension;
    private Vector3f position;
    private boolean respawn;
    /**
     * Will be serialized as optional not present if null
     * @since v712
     */
    private Integer loadingScreenId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CHANGE_DIMENSION;
    }
}
