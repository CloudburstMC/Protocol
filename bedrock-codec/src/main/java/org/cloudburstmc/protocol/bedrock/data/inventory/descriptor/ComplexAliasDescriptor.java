package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@Value
public class ComplexAliasDescriptor implements ItemDescriptor {
    String name;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.COMPLEX_ALIAS;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}