package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ContainerSetDataPacket implements BedrockPacket {

    public static final int FURNACE_TICK_COUNT = 0;
    public static final int FURNACE_LIT_TIME = 1;
    public static final int FURNACE_LIT_DURATION = 2;
    public static final int FURNACE_STORED_XP = 3;
    public static final int FURNACE_FUEL_AUX = 4;

    public static final int BREWING_STAND_BREW_TIME = 0;
    public static final int BREWING_STAND_FUEL_AMOUNT = 1;
    public static final int BREWING_STAND_FUEL_TOTAL = 2;

    private byte windowId;
    private int property;
    private int value;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONTAINER_SET_DATA;
    }

    @Override
    public ContainerSetDataPacket clone() {
        try {
            return (ContainerSetDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

