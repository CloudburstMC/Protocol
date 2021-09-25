package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class AgentCommandEventData implements EventData {
    int agentResult;
    String command;
    String dataKey;
    int dataValue;
    String output;

    @Override
    public EventDataType getType() {
        return EventDataType.AGENT_COMMAND;
    }
}
