package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class GameRule<T> {
    private final String name;
    private final T value;
}
