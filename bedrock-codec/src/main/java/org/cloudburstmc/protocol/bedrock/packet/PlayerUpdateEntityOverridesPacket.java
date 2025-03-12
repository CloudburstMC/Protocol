package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerUpdateEntityOverridesPacket implements BedrockPacket {
    private long targetID;
    private int propertyIndex;
    private UpdateType updateType;
    private int intValue;
    private float floatValue;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_UPDATE_ENTITY_OVERRIDES;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PlayerUpdateEntityOverridesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum UpdateType {
        CLEAR_OVERRIDES,
        REMOVE_OVERRIDE,
        SET_INT_OVERRIDE,
        SET_FLOAT_OVERRIDE
    }
}