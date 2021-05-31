package org.cloudburstmc.protocol.bedrock.data.defintions;

import com.nukkitx.nbt.NbtMap;
import lombok.Value;
import org.cloudburstmc.protocol.common.Definition;

@Value
public class BlockDefinition implements Definition {
    String identifier;
    int runtimeId;
    NbtMap state;
}
