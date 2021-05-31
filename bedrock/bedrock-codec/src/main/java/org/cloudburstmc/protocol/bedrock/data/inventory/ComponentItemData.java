package org.cloudburstmc.protocol.bedrock.data.inventory;

import com.nukkitx.nbt.NbtMap;
import lombok.Value;

@Value
public class ComponentItemData {

    private final String name;
    private final NbtMap data;
}
