package com.nukkitx.protocol.bedrock.codec.v575;

import io.netty.buffer.ByteBuf;
import com.nukkitx.protocol.bedrock.codec.EntityDataTypeMap;
import com.nukkitx.protocol.bedrock.codec.v568.BedrockCodecHelper_v568;
import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ComplexAliasDescriptor;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptor;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorType;
import com.nukkitx.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import com.nukkitx.protocol.common.util.TypeMap;

public class BedrockCodecHelper_v575 extends BedrockCodecHelper_v568 {

    public BedrockCodecHelper_v575(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes,
                                   TypeMap<Ability> abilities) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities);
    }

    @Override
    protected ItemDescriptor readItemDescriptor(ByteBuf buffer, ItemDescriptorType type) {
        if (type == ItemDescriptorType.COMPLEX_ALIAS) {
            String name = this.readString(buffer);
            return new ComplexAliasDescriptor(name);
        }
        return super.readItemDescriptor(buffer, type);
    }

    @Override
    protected void writeItemDescriptor(ByteBuf buffer, ItemDescriptor descriptor) {
        if (descriptor.getType() == ItemDescriptorType.COMPLEX_ALIAS) {
            this.writeString(buffer, ((ComplexAliasDescriptor) descriptor).getName());
        } else {
            super.writeItemDescriptor(buffer, descriptor);
        }
    }
}
