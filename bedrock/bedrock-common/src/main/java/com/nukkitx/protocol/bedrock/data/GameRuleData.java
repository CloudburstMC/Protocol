package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class GameRuleData<T> {
    private final String name;
    private final T value;

    @Override
    public String toString() {
        return this.name + '=' + this.value;
    }
}
