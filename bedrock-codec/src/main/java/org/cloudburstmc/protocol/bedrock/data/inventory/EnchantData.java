package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class EnchantData {
    private final int type;
    private final int level;
}
