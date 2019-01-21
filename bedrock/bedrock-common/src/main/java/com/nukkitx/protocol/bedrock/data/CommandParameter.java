package com.nukkitx.protocol.bedrock.data;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandParameter {
    @NonNull
    private final String name;
    private final int type;
    private final boolean optional;
    private final CommandEnum commandEnum;
    private final String postfix;
}
