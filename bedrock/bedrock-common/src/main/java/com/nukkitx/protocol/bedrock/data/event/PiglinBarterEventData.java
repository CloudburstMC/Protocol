package com.nukkitx.protocol.bedrock.data.event;

import lombok.Data;

@Data
public class PiglinBarterEventData implements EventData {
    private final int itemId;
    private final boolean targetingPlayer;

    @Override
    public EventDataType getType() {
        return EventDataType.PIGLIN_BARTER;
    }
}