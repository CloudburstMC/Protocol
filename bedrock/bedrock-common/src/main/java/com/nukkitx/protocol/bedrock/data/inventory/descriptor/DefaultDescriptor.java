package com.nukkitx.protocol.bedrock.data.inventory.descriptor;

import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.Value;

@Value
public class DefaultDescriptor implements ItemDescriptor {
    int itemId;
    int auxValue;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.DEFAULT;
    }

    @Override
    public ItemData.Builder toItem() {
        return ItemData.builder()
                .id(itemId)
                .damage(auxValue);
    }
}
