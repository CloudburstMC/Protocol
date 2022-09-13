package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

public interface ItemDescriptor {

    ItemDescriptorType getType();

    ItemData.Builder toItem();
}