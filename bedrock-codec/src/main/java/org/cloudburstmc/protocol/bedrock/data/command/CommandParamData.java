package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Data;

import java.util.EnumSet;
import java.util.Set;

@Data
public class CommandParamData {
    private String name;
    private boolean optional;
    private CommandEnumData enumData;
    private CommandParam type;
    private String postfix;
    private final Set<CommandParamOption> options = EnumSet.noneOf(CommandParamOption.class);
}
