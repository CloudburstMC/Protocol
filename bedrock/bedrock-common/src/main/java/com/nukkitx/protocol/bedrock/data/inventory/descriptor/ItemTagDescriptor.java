package com.nukkitx.protocol.bedrock.data.inventory.descriptor;

import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.Value;

@Value
public class ItemTagDescriptor implements ItemDescriptor {
    String itemTag;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.ITEM_TAG;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
