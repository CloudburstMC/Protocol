package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PetDiedEventData implements EventData {
    boolean ownerKilled;
    long killerUniqueEntityId;
    long petUniqueEntityId;
    int entityDamageCause;
    int petEntityType;

    @Override
    public EventDataType getType() {
        return EventDataType.PET_DIED;
    }
}
