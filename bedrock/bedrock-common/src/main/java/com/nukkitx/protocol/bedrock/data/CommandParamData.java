package com.nukkitx.protocol.bedrock.data;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
public class CommandParamData {
    @NonNull
    private final String name;
    private final boolean optional;
    private final CommandEnumData enumData;
    private final Type type;
    private final String postfix;

    public enum Type {
        INT(1),
        FLOAT(2),
        VALUE(3),
        WILDCARD_INT(4),
        OPERATOR(5),
        TARGET(6),
        WILDCARD_TARGET(7),
        FILE_PATH(14),
        INT_RANGE(18),
        STRING(26),
        POSITION(28),
        MESSAGE(31),
        TEXT(33),
        JSON(36),
        COMMAND(43);

        private static final TIntObjectMap<Type> BY_ID = new TIntObjectHashMap<>(15);

        static {
            for (Type type : values()) {
                BY_ID.put(type.id, type);
            }
        }

        @Getter
        private final int id;

        Type(int id) {
            this.id = id;
        }

        public static Type byId(int id) {
            return BY_ID.get(id);
        }
    }

    @Value
    public static class Builder {
        private final String name;
        private final CommandParamType type;
        private final boolean optional;
    }
}
