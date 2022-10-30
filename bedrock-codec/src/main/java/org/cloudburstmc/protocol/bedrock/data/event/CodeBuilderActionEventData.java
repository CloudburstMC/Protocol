package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Data;

@Data
public class CodeBuilderActionEventData implements EventData {
    private final String action;

    @Override
    public EventDataType getType() {
        return EventDataType.CODE_BUILDER_ACTION;
    }
}
