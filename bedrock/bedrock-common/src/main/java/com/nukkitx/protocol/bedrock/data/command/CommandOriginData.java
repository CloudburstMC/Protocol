package com.nukkitx.protocol.bedrock.data.command;

import lombok.Value;

import java.util.UUID;

@Value
public class CommandOriginData {
    CommandOriginType origin;
    UUID uuid;
    String requestId;
    long event;
}
