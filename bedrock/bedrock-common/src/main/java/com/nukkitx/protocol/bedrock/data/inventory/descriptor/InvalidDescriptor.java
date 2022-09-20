package com.nukkitx.protocol.bedrock.data.inventory.descriptor;

import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
