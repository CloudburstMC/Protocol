package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContainerSetDataPacket extends BedrockPacket {
    protected byte windowId;
    protected Property property;
    protected int value;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Property {
        FURNACE_TICK_COUNT,
        FURNACE_LIT_TIME,
        FURNACE_LIT_DURATION,
        FURNACE_FUEL_AUX,
        BREWING_STAND_BREW_TIME,
        BREWING_STAND_FUEL_AMOUNT,
        BREWING_STAND_FUEL_TOTAL
    }
}
