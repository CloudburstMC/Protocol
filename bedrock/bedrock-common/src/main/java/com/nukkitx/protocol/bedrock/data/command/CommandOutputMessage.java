package com.nukkitx.protocol.bedrock.data.command;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandOutputMessage {
    boolean internal;
    @NonNull
    String messageId;
    @NonNull
    String[] parameters;
}
