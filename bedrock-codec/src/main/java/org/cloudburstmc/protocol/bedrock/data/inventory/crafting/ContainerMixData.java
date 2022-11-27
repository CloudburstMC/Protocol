package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

import lombok.Value;

@Value
public class ContainerMixData {
    private final int inputId;
    private final int reagentId;
    private final int outputId;
}
