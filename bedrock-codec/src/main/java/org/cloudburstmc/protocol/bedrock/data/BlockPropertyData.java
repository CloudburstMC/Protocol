package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;
import org.cloudburstmc.nbt.NbtMap;

@Value
public class BlockPropertyData {
    private final String name;
    private final NbtMap properties;
}
