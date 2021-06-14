package com.nukkitx.protocol.bedrock.data.command;

import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Value
public class CommandData {
    private final String name;
    private final String description;
    private final List<Flag> flags;
    private final int permission;
    private final CommandEnumData aliases;
    private final CommandParamData[][] overloads;

    public String toString() {
        StringBuilder overloads = new StringBuilder("[\r\n");

        for (CommandParamData[] overload : this.overloads) {
            overloads.append("    [\r\n");
            for (CommandParamData parameter : overload) {
                overloads.append("       ").append(parameter).append("\r\n");
            }
            overloads.append("    ]\r\n");
        }
        overloads.append("]\r\n");

        StringBuilder builder = new StringBuilder("CommandData(\r\n");
        List<?> objects = Arrays.asList("name=" + name, "description=" + description,
                "flags=" + Arrays.toString(flags.toArray()), "permission=" + permission, "aliases=" + aliases,
                "overloads=" + overloads);

        for (Object object : objects) {
            builder.append("    ").append(Objects.toString(object).replaceAll("\r\n", "\r\n    ")).append("\r\n");
        }
        return builder.append(")").toString();
    }

    @Value
    public static class Builder {
        private final String name;
        private final String description;
        private final int flags;
        private final int permission;
        private final int aliases;
        private CommandParamData.Builder[][] overloads;
    }

    // Bit flags
    public enum Flag {
        USAGE,
        VISIBILITY,
        SYNC,
        EXECUTE,
        TYPE,
        CHEAT,
        UNKNOWN_6
    }
}
