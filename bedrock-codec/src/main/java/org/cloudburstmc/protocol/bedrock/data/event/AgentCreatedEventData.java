package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgentCreatedEventData implements EventData {
    public static final AgentCreatedEventData INSTANCE = new AgentCreatedEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.AGENT_CREATED;
    }
}
