package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.nbt.NbtMap;
import lombok.Value;

@Value
public class BlockPropertyData {
    private final String name;
    private final NbtMap properties;
}
