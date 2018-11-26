package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class EntityLink {
    private final long from;
    private final long to;
    private final Type type;
    private final boolean immediate;

    public enum Type {
        REMOVE,
        RIDER,
        PASSENGER
    }
}
