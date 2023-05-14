package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Data;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;

@Data
public class PiglinBarterEventData implements EventData {
    private final ItemDefinition definition;
    private final boolean targetingPlayer;

    @Override
    public EventDataType getType() {
        return EventDataType.PIGLIN_BARTER;
    }
}
