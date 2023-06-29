package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Value
public class CommandData {
    private final String name;
    private final String description;
    private final Set<Flag> flags;
    private final CommandPermission permission;
    private final CommandEnumData aliases;
    private final List<ChainedSubCommandData> subcommands;
    private final CommandOverloadData[] overloads;

    public String toString() {
        StringBuilder overloads = new StringBuilder("[\r\n");

        for (CommandOverloadData overload : this.overloads) {
            overloads.append("    [\r\n");
            overloads.append("       chaining=").append(overload.isChaining()).append("\r\n");
            for (CommandParamData parameter : overload.getOverloads()) {
                overloads.append("       ").append(parameter).append("\r\n");
            }
            overloads.append("    ]\r\n");
        }
        overloads.append("]\r\n");

        StringBuilder builder = new StringBuilder("CommandData(\r\n");
        List<?> objects = Arrays.asList("name=" + name, "description=" + description,
                "flags=" + Arrays.toString(flags.toArray()), "permission=" + permission, "aliases=" + aliases, "subcommands=" + Arrays.toString(subcommands.toArray()),
                "overloads=" + overloads);

        for (Object object : objects) {
            builder.append("    ").append(Objects.toString(object).replaceAll("\r\n", "\r\n    ")).append("\r\n");
        }
        return builder.append(")").toString();
    }

    // Bit flags
    public enum Flag {
        TEST_USAGE, // 1
        HIDDEN_FROM_COMMAND_BLOCK, // 2
        HIDDEN_FROM_PLAYER, // 4
        HIDDEN_FROM_AUTOMATION, // 8
        LOCAL_SYNC, // 16
        EXECUTE_DISALLOWED, // 32
        MESSAGE_TYPE, // 64
        NOT_CHEAT,// 128
        ASYNC // 256
    }
}
