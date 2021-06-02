package org.cloudburstmc.protocol.java.data.profile;

import lombok.Data;
import org.cloudburstmc.protocol.java.data.profile.property.PropertyMap;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.UUID;

@Data
@ParametersAreNullableByDefault
public class GameProfile {
    private UUID id;
    @Nullable private String name;
    @Nullable private PropertyMap properties;
    private boolean legacy;

    public GameProfile(UUID id) {
        this.id = id;
    }

    public GameProfile(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
