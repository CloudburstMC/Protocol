package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class FishBucketedEventData implements EventData {
    int pattern;
    int preset;
    int bucketedEntityType;
    boolean releaseEvent;

    @Override
    public EventDataType getType() {
        return EventDataType.FISH_BUCKETED;
    }
}
