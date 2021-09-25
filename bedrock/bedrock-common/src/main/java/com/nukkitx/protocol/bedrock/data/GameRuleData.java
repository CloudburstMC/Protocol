package com.nukkitx.protocol.bedrock.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class GameRuleData<T> {
    String name;
    boolean editable;
    T value;

    public GameRuleData(String name, T value) {
        this.name = name;
        this.editable = false;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name + '=' + this.value;
    }
}
