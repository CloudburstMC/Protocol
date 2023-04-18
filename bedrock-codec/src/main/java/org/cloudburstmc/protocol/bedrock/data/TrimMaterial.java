package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;

@Data
public class TrimMaterial {
    private final String materialId;
    private final String color;
    private final String itemName;
}
