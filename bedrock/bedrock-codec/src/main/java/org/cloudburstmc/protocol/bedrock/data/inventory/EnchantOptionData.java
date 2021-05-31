package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Value;

import java.util.List;

@Value
public class EnchantOptionData {
    private final int cost;
    private final int primarySlot;
    private final List<EnchantData> enchants0;
    private final List<EnchantData> enchants1;
    private final List<EnchantData> enchants2;
    private final String enchantName;
    private final int enchantNetId;
}
