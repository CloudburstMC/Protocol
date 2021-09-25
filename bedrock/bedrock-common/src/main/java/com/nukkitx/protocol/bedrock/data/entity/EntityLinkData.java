package com.nukkitx.protocol.bedrock.data.entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EntityLinkData {
    long from;
    long to;
    Type type;
    boolean immediate;
    boolean riderInitiated;

    @Deprecated
    public EntityLinkData(long from, long to, Type type, boolean immediate) {
        this(from, to, type, immediate, false);
    }

    public enum Type {
        REMOVE,
        RIDER,
        PASSENGER;

        private static final Type[] VALUES = values();

        public static Type byId(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            }
            throw new UnsupportedOperationException("Unknown EntityLinkData.Type ID: " + id);
        }
    }
}
