package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class CauldronBlockUsedEventData implements EventData {
    private final int blockInteractionType;
    private final int unknown0;

    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_BLOCK_USED;
    }
}
