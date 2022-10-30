package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Value;
import org.cloudburstmc.nbt.NbtMap;

@Value
public class ComponentItemData {

    private final String name;
    private final NbtMap data;
}
