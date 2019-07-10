package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class AgentCommandEventData implements EventData {
    private final int agentResult;
    private final String command;
    private final String dataKey;
    private final int dataValue;
    private final String output;

    @Override
    public EventDataType getType() {
        return EventDataType.AGENT_COMMAND;
    }
}
