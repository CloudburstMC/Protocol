package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

import java.util.List;

@Value
public class SlashCommandExecutedEventData implements EventData {
    private final String commandName;
    private final int successCount;
    private final List<String> outputMessages;

    @Override
    public EventDataType getType() {
        return EventDataType.SLASH_COMMAND_EXECUTED;
    }
}
