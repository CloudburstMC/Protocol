package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@Value
public class MolangDescriptor implements ItemDescriptor {
    String tagExpression;
    int molangVersion;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.MOLANG;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}