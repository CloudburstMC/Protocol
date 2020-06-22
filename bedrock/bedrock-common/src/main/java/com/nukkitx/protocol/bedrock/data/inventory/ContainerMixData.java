package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class ContainerMixData {
    private final int fromItemId;
    private final int ingredient;
    private final int toItemId;
}
