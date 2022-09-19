package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.CraftingDataSerializer_v465;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.*;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CraftingDataSerializer_v554 extends CraftingDataSerializer_v465 {

    protected static final ItemDescriptorType[] TYPES = ItemDescriptorType.values();

    @Override
    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockCodecHelper helper) {
        ItemDescriptorType type = TYPES[buffer.readUnsignedByte()];

        ItemDescriptor descriptor;
        switch (type) {
            case DEFAULT:
                int itemId = buffer.readShortLE();
                ItemDefinition definition = itemId == 0 ? ItemDefinition.AIR : helper.getItemDefinitions().getDefinition(itemId);
                int auxValue = itemId != 0 ? buffer.readShortLE() : 0;
                descriptor = new DefaultDescriptor(definition, auxValue);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(helper.readString(buffer), buffer.readUnsignedByte());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(helper.readString(buffer));
                break;
            case DEFERRED:
                descriptor = new DeferredDescriptor(helper.readString(buffer), buffer.readShortLE());
                break;
            default:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
        }

        return new ItemDescriptorWithCount(descriptor, VarInts.readInt(buffer));
    }

    @Override
    protected void writeIngredient(ByteBuf buffer, BedrockCodecHelper helper, ItemDescriptorWithCount ingredient) {
        ItemDescriptorType type = ingredient.getDescriptor().getType();
        buffer.writeByte(type.ordinal());

        switch (type) {
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) ingredient.getDescriptor();
                boolean empty = defaultDescriptor.getItemId() == null || defaultDescriptor.getItemId().getRuntimeId() == 0;
                buffer.writeShortLE(empty ? 0 : defaultDescriptor.getItemId().getRuntimeId());
                if (!empty) {
                    buffer.writeShortLE(defaultDescriptor.getAuxValue());
                }
                break;
            case MOLANG:
                MolangDescriptor molangDescriptor = (MolangDescriptor) ingredient.getDescriptor();
                helper.writeString(buffer, molangDescriptor.getTagExpression());
                buffer.writeByte(molangDescriptor.getMolangVersion());
                break;
            case ITEM_TAG:
                ItemTagDescriptor tagDescriptor = (ItemTagDescriptor) ingredient.getDescriptor();
                helper.writeString(buffer, tagDescriptor.getItemTag());
                break;
            case DEFERRED:
                DeferredDescriptor deferredDescriptor = (DeferredDescriptor) ingredient.getDescriptor();
                helper.writeString(buffer, deferredDescriptor.getFullName());
                buffer.writeShortLE(deferredDescriptor.getAuxValue());
                break;
        }

        VarInts.writeInt(buffer, ingredient.getCount());
    }
}