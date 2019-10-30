package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class ContainerMixData {
    private final int fromItemId;
    private final int ingredient;
    private final int toItemId;
}
