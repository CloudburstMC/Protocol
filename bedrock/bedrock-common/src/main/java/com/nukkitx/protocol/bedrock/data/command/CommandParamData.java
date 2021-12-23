package com.nukkitx.protocol.bedrock.data.command;

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
    private final CommandParam type;
    private final String postfix;
    private final List<CommandParamOption> options; // Bit flags. Gamerule command is the only one to use it.

    @Value
    @RequiredArgsConstructor
    public static class Builder {
        private final String name;
        private final CommandSymbolData type;
        private final boolean optional;
        private final byte options;

        @Deprecated
        public Builder(String name, CommandSymbolData type, boolean optional) {
            this(name, type, optional, (byte) 0);
        }
    }
}
