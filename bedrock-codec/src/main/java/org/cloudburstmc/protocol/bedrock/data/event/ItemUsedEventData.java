package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class ItemUsedEventData implements EventData {
    private final short itemId;
    private final int itemAux;
    private final int useMethod;
    private final int useCount;

    @Override
    public EventDataType getType() {
        return EventDataType.ITEM_USED_EVENT;
    }
}