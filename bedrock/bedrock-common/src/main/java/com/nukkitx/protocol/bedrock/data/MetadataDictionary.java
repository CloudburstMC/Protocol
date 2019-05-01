package com.nukkitx.protocol.bedrock.data;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.network.util.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;

public class MetadataDictionary extends EnumMap<EntityData, Object> {

    public MetadataDictionary() {
        super(EntityData.class);
    }

    private static boolean isAcceptable(Object o) {
        return o instanceof EntityFlags ||
                o instanceof Byte ||
                o instanceof Short ||
                o instanceof Integer ||
                o instanceof Float ||
                o instanceof String ||
                o instanceof ItemData ||
                o instanceof Vector3i ||
                o instanceof Long ||
                o instanceof Vector3f;
    }

    @Nonnull
    public static EntityData.Type getType(Object o) {
        if (o instanceof EntityFlags) {
            return EntityData.Type.FLAGS;
        } else if (o instanceof Byte) {
            return EntityData.Type.BYTE;
        } else if (o instanceof Short) {
            return EntityData.Type.SHORT;
        } else if (o instanceof Integer) {
            return EntityData.Type.INT;
        } else if (o instanceof Float) {
            return EntityData.Type.FLOAT;
        } else if (o instanceof String) {
            return EntityData.Type.STRING;
        } else if (o instanceof ItemData) {
            return EntityData.Type.ITEM;
        } else if (o instanceof Vector3i) {
            return EntityData.Type.VECTOR3I;
        } else if (o instanceof Long) {
            return EntityData.Type.LONG;
        } else if (o instanceof Vector3f) {
            return EntityData.Type.VECTOR3F;
        }
        throw new IllegalArgumentException("Invalid type");
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T get(EntityData entityData) {
        return (T) super.get(entityData);
    }

    @Override
    public Object put(@Nonnull EntityData entityData, @Nonnull Object o) {
        Preconditions.checkNotNull(entityData, "dictionary");
        Preconditions.checkNotNull(o, "o");
        Preconditions.checkArgument(isAcceptable(o), "%s is an unacceptable metadata type", o.getClass().getSimpleName());

        return super.put(entityData, o);
    }

    public EntityFlags getFlags() {
        return get(EntityData.FLAGS);
    }

    public void putFlags(@Nonnull EntityFlags flags) {
        put(EntityData.FLAGS, flags);
    }
}
