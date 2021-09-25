package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class EntityInteractEventData implements EventData {
    int interactionType;
    int legacyEntityTypeId;
    int variant;
    int paletteColor;

    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_INTERACT;
    }
}
