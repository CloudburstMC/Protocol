package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Data;

@Data
public class TargetBlockHitEventData implements EventData {
    private final int redstoneLevel;

    @Override
    public EventDataType getType() {
        return EventDataType.TARGET_BLOCK_HIT;
    }
}
