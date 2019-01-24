package com.nukkitx.protocol.bedrock.data;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandEnumData {
    @NonNull
    private final String name;
    @NonNull
    private final String[] values;
    private final boolean isSoft;

    public int hashCode() {
        return name.hashCode();
    }
}
