package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PatternRemovedEventData implements EventData {
    private final int itemId;
    private final int auxValue;
    private final int patternsSize;
    private final int patternIndex;
    private final int patternColor;

    @Override
    public EventDataType getType() {
        return EventDataType.PATTERN_REMOVED;
    }
}
