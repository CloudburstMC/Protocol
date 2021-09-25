package com.nukkitx.protocol.bedrock.data.event;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@RequiredArgsConstructor
@AllArgsConstructor
public class MobKilledEventData implements EventData {
    long killerUniqueEntityId;
    long victimUniqueEntityId;
    int killerEntityType;
    int entityDamageCause;
    @NonFinal
    int villagerTradeTier = -1;
    @NonFinal
    String villagerDisplayName = "";

    @Override
    public EventDataType getType() {
        return EventDataType.MOB_KILLED;
    }
}
