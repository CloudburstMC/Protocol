package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.nbt.NbtMap;
import lombok.Value;

@Value
public class BlockPropertyData {
    String name;
    NbtMap properties;
}
