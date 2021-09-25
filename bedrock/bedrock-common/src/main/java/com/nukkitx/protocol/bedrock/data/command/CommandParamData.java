package com.nukkitx.protocol.bedrock.data.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class CommandParamData {
    @NonNull
    String name;
    boolean optional;
    CommandEnumData enumData;
    CommandParam type;
    String postfix;
    List<CommandParamOption> options; // Bit flags. Gamerule command is the only one to use it.

    @Value
    @RequiredArgsConstructor
    public static class Builder {
        String name;
        CommandSymbolData type;
        boolean optional;
        byte options;

        @Deprecated
        public Builder(String name, CommandSymbolData type, boolean optional) {
            this(name, type, optional, (byte) 0);
        }
    }
}
