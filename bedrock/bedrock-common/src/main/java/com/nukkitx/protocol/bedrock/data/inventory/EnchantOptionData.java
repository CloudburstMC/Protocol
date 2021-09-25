package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

import java.util.List;

@Value
public class EnchantOptionData {
    int cost;
    int primarySlot;
    List<EnchantData> enchants0;
    List<EnchantData> enchants1;
    List<EnchantData> enchants2;
    String enchantName;
    int enchantNetId;
}
