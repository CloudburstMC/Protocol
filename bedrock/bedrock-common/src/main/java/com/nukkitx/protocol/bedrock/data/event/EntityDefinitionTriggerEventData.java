package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class EntityDefinitionTriggerEventData implements EventData {
    private final String id;

    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_DEFINITION_TRIGGER;
    }
}
