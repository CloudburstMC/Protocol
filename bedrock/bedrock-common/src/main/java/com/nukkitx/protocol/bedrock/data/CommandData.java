package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class CommandData {
    private final String name;
    private final String description;
    private final Flag[] flags;
    private final byte permission;
    private final CommandEnum aliases;
    private final CommandParameter[][] overloads;

    // Bit flags
    public enum Flag {
        USAGE,
        VISIBILITY,
        SYNC,
        EXECUTE,
        TYPE,
        CHEAT
    }
}
