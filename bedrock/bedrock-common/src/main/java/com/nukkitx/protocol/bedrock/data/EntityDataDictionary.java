package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.network.util.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;

public class EntityDataDictionary extends EnumMap<EntityData, Object> {

    public EntityDataDictionary() {
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
                o instanceof CompoundTag ||
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
        } else if (o instanceof ItemData || o instanceof CompoundTag) {
            return EntityData.Type.NBT;
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

    public boolean contains(EntityData entityData) {
        return this.containsKey(entityData);
    }

    @Nullable
    public EntityData.Type getType(EntityData entityData) {
        Object object = this.get(entityData);
        if (object == null) {
            // Try default type
            return entityData.getType();
        } else {
            return EntityDataDictionary.getType(object);
        }
    }

    @Override
    public Object put(@Nonnull EntityData entityData, @Nonnull Object o) {
        Preconditions.checkNotNull(entityData, "dictionary");
        Preconditions.checkNotNull(o, "o");
        Preconditions.checkArgument(isAcceptable(o), "%s is an unacceptable metadata type", o.getClass().getSimpleName());

        if (entityData == EntityData.FLAGS || entityData == EntityData.FLAGS_2) {
            Preconditions.checkArgument(getType(o) == EntityData.Type.FLAGS, "Invalid class for flags");
            this.putFlags((EntityFlags) o);
            return null;
        }

        return super.put(entityData, o);
    }

    public EntityFlags getFlags() {
        return get(EntityData.FLAGS);
    }

    public void putFlags(@Nonnull EntityFlags flags) {
        EntityFlags originalFlags = this.get(EntityData.FLAGS);
        if (originalFlags != null) {
            originalFlags.merge(flags);
        } else {
            super.put(EntityData.FLAGS, flags);
            super.put(EntityData.FLAGS_2, flags);
        }
    }
}
