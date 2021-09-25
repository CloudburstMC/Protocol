package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PatternRemovedEventData implements EventData {
    int itemId;
    int auxValue;
    int patternsSize;
    int patternIndex;
    int patternColor;

    @Override
    public EventDataType getType() {
        return EventDataType.PATTERN_REMOVED;
    }
}
