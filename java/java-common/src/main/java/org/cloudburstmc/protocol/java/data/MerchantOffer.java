package org.cloudburstmc.protocol.java.data;

import lombok.Value;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Value
public class MerchantOffer {
    ItemStack buyA;
    ItemStack buyB;
    ItemStack sell;
    int uses;
    int maxUses;
    boolean rewardExp;
    int specialPriceDiff;
    int demand;
    float priceMultiplier;
    int xp;
}
