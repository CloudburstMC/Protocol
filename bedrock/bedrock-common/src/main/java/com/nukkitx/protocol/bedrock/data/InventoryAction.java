package com.nukkitx.protocol.bedrock.data;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Value;

import javax.annotation.Nonnull;

@Value
public class InventoryAction {
    private final InventorySource source;
    private final int flags;
    private final int slot;
    private final Item oldItem;
    private final Item newItem;

    public enum Type {
        UNKNOWN(-1),
        CONTAINER(0),
        GLOBAL(1),
        WORLD(2),
        CREATIVE(3),
        CRAFT_SLOT(100),
        CRAFT(99999);

        private static final TIntObjectMap<Type> BY_ID = new TIntObjectHashMap<>(5);

        static {
            for (Type type : values()) {
                BY_ID.put(type.id, type);
            }
        }

        private final int id;

        Type(int id) {
            this.id = id;
        }

        @Nonnull
        public static Type byId(int id) {
            Type type = BY_ID.get(id);
            if (type == null) {
                return UNKNOWN;
            }
            return type;
        }

        public int id() {
            return id;
        }
    }
}
