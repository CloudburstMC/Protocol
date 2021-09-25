package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

import java.util.List;

@Value
public class SlashCommandExecutedEventData implements EventData {
    String commandName;
    int successCount;
    List<String> outputMessages;

    @Override
    public EventDataType getType() {
        return EventDataType.SLASH_COMMAND_EXECUTED;
    }
}
