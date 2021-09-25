package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

/**
 * Represents a potion mixing recipe which may be used in a brewing stand.
 */
@Value
public class PotionMixData {
    // Potion to be put in
    int inputId;
    int inputMeta;

    // Item to be added to the brewing stand to brew the output potion
    int reagentId;
    int reagentMeta;

    // Output Potion
    int outputId;
    int outputMeta;
}
