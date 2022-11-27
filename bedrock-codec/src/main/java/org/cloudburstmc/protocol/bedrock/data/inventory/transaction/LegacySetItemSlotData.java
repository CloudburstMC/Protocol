package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

import lombok.Value;

@Value
public class LegacySetItemSlotData {
    private final int containerId;
    private final byte[] slots;
}
