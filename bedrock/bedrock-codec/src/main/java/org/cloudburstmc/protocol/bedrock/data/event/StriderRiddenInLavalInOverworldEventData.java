package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StriderRiddenInLavalInOverworldEventData implements EventData {
    public static final StriderRiddenInLavalInOverworldEventData INSTANCE = new StriderRiddenInLavalInOverworldEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD;
    }
}
