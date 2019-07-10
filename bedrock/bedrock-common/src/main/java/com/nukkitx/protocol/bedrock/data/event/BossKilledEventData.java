package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BossKilledEventData implements EventData {
    private final long bossUniqueEntityId;
    private final int playerPartySize;
    private final int legacyEntityTypeId;

    @Override
    public EventDataType getType() {
        return EventDataType.BOSS_KILLED;
    }
}
