package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Value;

import java.util.LinkedHashMap;
import java.util.Set;

@Value
public class CommandEnumData {
    String name;
    LinkedHashMap<String, Set<CommandEnumConstraint>> values;
    boolean isSoft;
}
