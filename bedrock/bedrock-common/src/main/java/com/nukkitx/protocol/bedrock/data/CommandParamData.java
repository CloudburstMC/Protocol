package com.nukkitx.protocol.bedrock.data;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class CommandParamData {
    @NonNull
    private final String name;
    private final boolean optional;
    private final CommandEnumData enumData;
    private final Type type;
    private final String postfix;
    private final List<Option> options; // Bit flags. Gamerule command is the only one to use it.

    public enum Type {
        INT,
        FLOAT,
        VALUE,
        WILDCARD_INT,
        OPERATOR,
        TARGET,
        WILDCARD_TARGET,
        FILE_PATH,
        INT_RANGE,
        STRING,
        POSITION,
        MESSAGE,
        TEXT,
        JSON,
        COMMAND
    }

    public enum Option {
        UNKNOWN_0,
        UNKNOWN_1
    }

    @Value
    @RequiredArgsConstructor
    public static class Builder {
        private final String name;
        private final CommandParamType type;
        private final boolean optional;
        private final byte options;

        public Builder(String name, CommandParamType type, boolean optional) {
            this.name = name;
            this.type = type;
            this.optional = optional;
            this.options = 0;
        }
    }
}
