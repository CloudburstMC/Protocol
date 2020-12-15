package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class EntityInteractEventData implements EventData {
    private final int interactionType;
    private final int legacyEntityTypeId;
    private final int variant;
    private final int paletteColor;

    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_INTERACT;
    }
}
