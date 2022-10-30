package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class EntityDefinitionTriggerEventData implements EventData {
    private final String eventName;

    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_DEFINITION_TRIGGER;
    }
}
