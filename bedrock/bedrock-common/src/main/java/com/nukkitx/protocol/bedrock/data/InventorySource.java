package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

import java.util.Objects;

@Value
public final class InventorySource {
    private final InventoryAction.Type type;
    private final int id;

    public static InventorySource of(InventoryAction.Type type, int id) {
        Objects.requireNonNull(type, "transactionType");
        return new InventorySource(type, id);
    }
}