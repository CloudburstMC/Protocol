package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Data;

@Data
public class CommandOverloadData {
    private final boolean chaining;
    private final CommandParamData[] overloads;
}
