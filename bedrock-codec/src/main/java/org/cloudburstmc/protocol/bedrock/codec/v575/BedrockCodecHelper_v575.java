package org.cloudburstmc.protocol.bedrock.codec.v575;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v568.BedrockCodecHelper_v568;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ComplexAliasDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.common.DefinitionRegistry;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class BedrockCodecHelper_v575 extends BedrockCodecHelper_v568 {

    @Getter
    @Setter
    protected DefinitionRegistry<NamedDefinition> cameraPresetDefinitions;

    public BedrockCodecHelper_v575(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes,
                                   TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
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
