package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandOutputMessage {
    private final boolean internal;
    @NonNull
    private final String messageId;
    @NonNull
    private final String[] parameters;
}
