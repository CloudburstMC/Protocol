package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class RaidUpdateEventData implements EventData {
    int currentWave;
    int totalWaves;
    boolean winner;

    @Override
    public EventDataType getType() {
        return EventDataType.RAID_UPDATE;
    }
}
