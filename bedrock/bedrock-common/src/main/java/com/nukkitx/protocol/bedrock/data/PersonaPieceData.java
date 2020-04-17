package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class PersonaPieceData {
    private final String id;
    private final String type;
    private final String packId;
    private final boolean isDefault;
    private final String productId;
}
