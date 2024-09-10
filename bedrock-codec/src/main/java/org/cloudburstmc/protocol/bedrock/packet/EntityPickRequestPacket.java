package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EntityPickRequestPacket implements BedrockPacket {
    private long runtimeEntityId;
    private int hotbarSlot;
    /**
     * @since v465
     */
    private boolean withData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ENTITY_PICK_REQUEST;
    }

    @Override
    public EntityPickRequestPacket clone() {
        try {
            return (EntityPickRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

