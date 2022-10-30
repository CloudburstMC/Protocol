package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtractHoneyEventData implements EventData {
    public static final ExtractHoneyEventData INSTANCE = new ExtractHoneyEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.EXTRACT_HONEY;
    }
}
