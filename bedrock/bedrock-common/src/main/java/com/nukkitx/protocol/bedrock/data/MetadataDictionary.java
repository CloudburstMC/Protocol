package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.network.util.Preconditions;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Optional;

public class MetadataDictionary extends EnumMap<Metadata, Object> {
    private static final Metadata.Type[] TYPES = Metadata.Type.values();

    public MetadataDictionary() {
        super(Metadata.class);
    }

    private static boolean isAcceptable(Metadata metadata, Object o) {
        return metadata.getType() == null || metadata.getType().typeClass() == o.getClass();
    }

    @Override
    public Object put(@Nonnull Metadata metadata, @Nonnull Object o) {
        Preconditions.checkNotNull(metadata, "dictionary");
        Preconditions.checkNotNull(o, "o");
        Preconditions.checkArgument(isAcceptable(metadata, o), "%s is not of type %s but %s", metadata,
                o.getClass().getSimpleName(), metadata.getType() == null ? null : metadata.getType().typeClass().getSimpleName());

        return super.put(metadata, o);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Metadata metadata) {
        return Optional.ofNullable((T) super.get(metadata));
    }
}
