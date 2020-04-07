package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

import java.util.List;

@Value
public class EnchantOptionData {
    private final int varInt0;
    private final int primarySlot;
    private final List<EnchantData> slot0;
    private final List<EnchantData> slot1;
    private final List<EnchantData> slot2;
    private String description;
}
