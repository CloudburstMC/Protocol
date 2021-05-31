package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandEnumData {
    @NonNull
    private final String name;
    @NonNull
    private final String[] values;
    private final boolean isSoft;
}
