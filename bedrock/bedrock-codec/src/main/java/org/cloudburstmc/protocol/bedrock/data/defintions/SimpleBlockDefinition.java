package org.cloudburstmc.protocol.bedrock.data.defintions;

import com.nukkitx.nbt.NbtMap;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SimpleBlockDefinition extends BlockDefinition {
    String identifier;
    int runtimeId;
    NbtMap state;
}
