package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@RequiredArgsConstructor
@AllArgsConstructor
public class MobKilledEventData implements EventData {
    private final long killerUniqueEntityId;
    private final long victimUniqueEntityId;
    private final int killerEntityType;
    private final int entityDamageCause;
    @NonFinal
    private int villagerTradeTier = -1;
    @NonFinal
    private String villagerDisplayName = "";

    @Override
    public EventDataType getType() {
        return EventDataType.MOB_KILLED;
    }
}
