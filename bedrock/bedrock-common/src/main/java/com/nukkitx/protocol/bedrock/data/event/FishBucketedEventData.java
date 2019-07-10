package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class FishBucketedEventData implements EventData {
    private final int unknown0;
    private final int unknown1;
    private final int unknown2;
    private final boolean unknown3;

    @Override
    public EventDataType getType() {
        return EventDataType.FISH_BUCKETED;
    }
}
