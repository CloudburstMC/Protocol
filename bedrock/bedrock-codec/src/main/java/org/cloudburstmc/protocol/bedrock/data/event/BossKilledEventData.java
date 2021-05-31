package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BossKilledEventData implements EventData {
    private final long bossUniqueEntityId;
    private final int playerPartySize;
    private final int bossEntityType;

    @Override
    public EventDataType getType() {
        return EventDataType.BOSS_KILLED;
    }
}
