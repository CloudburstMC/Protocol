package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class LegacySetItemSlotData {
    int containerId;
    byte[] slots;
}
