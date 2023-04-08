package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Value;

import java.util.Map;
import java.util.Set;

@Value
public class CommandEnumData {
    String name;
    Map<String, Set<CommandEnumConstraint>> values;
    boolean isSoft;
}
