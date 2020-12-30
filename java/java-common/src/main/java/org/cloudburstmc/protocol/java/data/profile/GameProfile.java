package org.cloudburstmc.protocol.java.data.profile;

import lombok.Data;
import org.cloudburstmc.protocol.java.data.profile.property.PropertyMap;

import java.util.UUID;

@Data
public class GameProfile {
    private final UUID id;
    private final String name;
    private PropertyMap properties;
    private boolean legacy;
}
