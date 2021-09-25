package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BossKilledEventData implements EventData {
    long bossUniqueEntityId;
    int playerPartySize;
    int bossEntityType;

    @Override
    public EventDataType getType() {
        return EventDataType.BOSS_KILLED;
    }
}
