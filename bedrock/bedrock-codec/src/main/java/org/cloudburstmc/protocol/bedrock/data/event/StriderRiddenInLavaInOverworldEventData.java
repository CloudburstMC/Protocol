package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StriderRiddenInLavaInOverworldEventData implements EventData {
    public static final StriderRiddenInLavaInOverworldEventData INSTANCE = new StriderRiddenInLavaInOverworldEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD;
    }
}
