package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@Value
public class DeferredDescriptor implements ItemDescriptor {
    String fullName;
    int auxValue;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.DEFERRED;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}