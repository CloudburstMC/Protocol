package com.nukkitx.protocol.bedrock.data.command;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandEnumData {
    @NonNull
    String name;
    @NonNull
    String[] values;
    boolean isSoft;
}
