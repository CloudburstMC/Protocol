package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PetDiedEventData implements EventData {
    private final boolean ownerKilled;
    private final long killerUniqueEntityId;
    private final long petUniqueEntityId;
    private final int entityDamageCause;
    private final int petEntityType;

    @Override
    public EventDataType getType() {
        return EventDataType.PET_DIED;
    }
}
