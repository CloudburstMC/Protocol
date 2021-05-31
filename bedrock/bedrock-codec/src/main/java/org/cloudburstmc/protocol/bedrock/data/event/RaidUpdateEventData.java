package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class RaidUpdateEventData implements EventData {
    private final int currentWave;
    private final int totalWaves;
    private final boolean winner;

    @Override
    public EventDataType getType() {
        return EventDataType.RAID_UPDATE;
    }
}
