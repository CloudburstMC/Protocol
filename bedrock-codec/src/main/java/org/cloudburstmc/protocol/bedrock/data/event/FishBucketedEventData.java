package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class FishBucketedEventData implements EventData {
    private final int pattern;
    private final int preset;
    private final int bucketedEntityType;
    private final boolean releaseEvent;

    @Override
    public EventDataType getType() {
        return EventDataType.FISH_BUCKETED;
    }
}
