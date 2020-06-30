package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

@Value
@Immutable
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemInstance {

    int networkId;
    ItemData item;

    public static ItemInstance of(ItemData item) {
        return of(item.isValid() ? 1 : 0, item);
    }

    public static ItemInstance of(int networkId, ItemData item) {
        return new ItemInstance(networkId, item);
    }
}
