package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Value;

import java.util.EnumSet;
import java.util.LinkedHashMap;

@Value
public class CommandEnumData {
    String name;
    LinkedHashMap<String, EnumSet<CommandEnumConstraint>> values;
    boolean isSoft;
}
