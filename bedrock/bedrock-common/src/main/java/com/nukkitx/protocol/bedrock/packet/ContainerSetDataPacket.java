package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContainerSetDataPacket extends BedrockPacket {

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
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONTAINER_SET_DATA;
    }
}
