package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

import java.util.UUID;

@Value
public class CommandOriginData {
    private final Origin origin;
    private final UUID uuid;
    private final String requestId;
    private final long event;

    public enum Origin {
        PLAYER,
        BLOCK,
        MINECART_BLOCK,
        DEV_CONSOLE,
        TEST,
        AUTOMATION_PLAYER,
        CLIENT_AUTOMATION,
        DEDICATED_SERVER,
        ENTITY,
        VIRTUAL,
        GAME_ARGUMENT,
        ENTITY_SERVER,
        PRECOMPILED,
        GAME_MASTER_ENTITY_SERVER,
        SCRIPT,
    }
}
