package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MovementThresholdEventData implements EventData {
    private final float unknown0;
    private final float unknown1;
    private final float playerMovementScoreThreshold;
    private final float playerMovementDistanceThreshold;
    private final int playerMovementDurationThreshold;

    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_THRESHOLD;
    }
}
