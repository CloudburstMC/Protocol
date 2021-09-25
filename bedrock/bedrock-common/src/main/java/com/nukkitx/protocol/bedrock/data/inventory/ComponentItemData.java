package com.nukkitx.protocol.bedrock.data.inventory;

import com.nukkitx.nbt.NbtMap;
import lombok.Value;

@Value
public class ComponentItemData {

    String name;
    NbtMap data;
}
