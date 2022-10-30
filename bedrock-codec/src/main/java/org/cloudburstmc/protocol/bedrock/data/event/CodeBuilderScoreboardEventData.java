package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Data;

@Data
public class CodeBuilderScoreboardEventData implements EventData {
    private final String objectiveName;
    private final int score;

    @Override
    public EventDataType getType() {
        return EventDataType.CODE_BUILDER_SCOREBOARD;
    }
}
