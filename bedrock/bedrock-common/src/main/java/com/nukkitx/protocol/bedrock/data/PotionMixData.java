package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class PotionMixData {
    private final int fromPotionId;
    private final int ingredient;
    private final int toPotionId;
}
