package com.nukkitx.protocol.bedrock.data;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.network.util.Preconditions;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Optional;

public class MetadataDictionary extends EnumMap<Metadata, Object> {

    public MetadataDictionary() {
        super(Metadata.class);
    }

    private static boolean isAcceptable(Object o) {
        return o instanceof MetadataFlags ||
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
    public static Metadata.Type getType(Object o) {
        if (o instanceof MetadataFlags) {
            return Metadata.Type.FLAGS;
        } else if (o instanceof Byte) {
            return Metadata.Type.BYTE;
        } else if (o instanceof Short) {
            return Metadata.Type.SHORT;
        } else if (o instanceof Integer) {
            return Metadata.Type.INT;
        } else if (o instanceof Float) {
            return Metadata.Type.FLOAT;
        } else if (o instanceof String) {
            return Metadata.Type.STRING;
        } else if (o instanceof ItemData) {
            return Metadata.Type.ITEM;
        } else if (o instanceof Vector3i) {
            return Metadata.Type.VECTOR3I;
        } else if (o instanceof Long) {
            return Metadata.Type.LONG;
        } else if (o instanceof Vector3f) {
            return Metadata.Type.VECTOR3F;
        }
        throw new IllegalArgumentException("Invalid type");
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Metadata metadata) {
        return Optional.ofNullable((T) super.get(metadata));
    }

    @Override
    public Object put(@Nonnull Metadata metadata, @Nonnull Object o) {
        Preconditions.checkNotNull(metadata, "dictionary");
        Preconditions.checkNotNull(o, "o");
        Preconditions.checkArgument(isAcceptable(o), "%s is an unacceptable metadata type", o.getClass().getSimpleName());

        return super.put(metadata, o);
    }
}
