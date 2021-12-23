package com.nukkitx.protocol.bedrock.data.inventory;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import lombok.Value;

@Value
public class MaterialReducer {
    int inputId;
    Int2IntMap itemCounts;
}
