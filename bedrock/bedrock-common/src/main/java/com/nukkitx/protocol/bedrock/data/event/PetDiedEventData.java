package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PetDiedEventData implements EventData {
    private final boolean unknown0;
    private final long killerUniqueEntityId;
    private final long petUniqueEntityId;
    private final int entityDamageCause;
    private final int unknown1;

    @Override
    public EventDataType getType() {
        return EventDataType.PET_DIED;
    }
}
