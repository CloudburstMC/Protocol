package org.cloudburstmc.protocol.bedrock.data.defintions;

import com.nukkitx.nbt.NbtMap;
import org.cloudburstmc.protocol.common.Definition;

public abstract class BlockDefinition implements Definition {
    public abstract NbtMap getState();
}