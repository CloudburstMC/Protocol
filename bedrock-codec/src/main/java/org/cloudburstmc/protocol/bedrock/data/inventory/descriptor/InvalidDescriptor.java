package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidDescriptor implements ItemDescriptor {

    public static final InvalidDescriptor INSTANCE = new InvalidDescriptor();

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.INVALID;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}