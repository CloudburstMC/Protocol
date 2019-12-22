package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class RaidUpdateEventData implements EventData {
    private final int currentWave;
    private final int totalWaves;
    private final boolean unknown0; // Hero of the village?

    @Override
    public EventDataType getType() {
        return EventDataType.RAID_UPDATE;
    }
}
