package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@Value
public class DefaultDescriptor implements ItemDescriptor {
    ItemDefinition itemId;
    int auxValue;

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.DEFAULT;
    }

    @Override
    public ItemData.Builder toItem() {
        return ItemData.builder()
                .definition(itemId)
                .damage(auxValue);
    }
}