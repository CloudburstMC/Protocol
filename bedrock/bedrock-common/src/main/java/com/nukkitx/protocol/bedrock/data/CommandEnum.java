package com.nukkitx.protocol.bedrock.data;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandEnum {
    @NonNull
    private final String name;
    @NonNull
    private final String[] values;

    public int hashCode() {
        return name.hashCode();
    }
}
