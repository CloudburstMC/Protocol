package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Value;

import java.util.UUID;

@Value
public class CommandOriginData {
    private final CommandOriginType origin;
    private final UUID uuid;
    private final String requestId;
    private final long event;
}
