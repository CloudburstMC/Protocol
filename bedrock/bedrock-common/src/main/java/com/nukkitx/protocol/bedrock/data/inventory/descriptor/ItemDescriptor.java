package com.nukkitx.protocol.bedrock.data.inventory.descriptor;

import com.nukkitx.protocol.bedrock.data.inventory.ItemData;

public interface ItemDescriptor {

    ItemDescriptorType getType();

    ItemData.Builder toItem();
}
