package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarefulRestorationEventData implements EventData {
    public static final CarefulRestorationEventData INSTANCE = new CarefulRestorationEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.CAREFUL_RESTORATION;
    }
}
