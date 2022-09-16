package com.nukkitx.protocol.bedrock.v554.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.*;
import com.nukkitx.protocol.bedrock.v465.serializer.CraftingDataSerializer_v465;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CraftingDataSerializer_v554 extends CraftingDataSerializer_v465 {

    public static final CraftingDataSerializer_v554 INSTANCE = new CraftingDataSerializer_v554();

    protected static final ItemDescriptorType[] TYPES = ItemDescriptorType.values();

    @Override
    protected ItemDescriptorWithCount readIngredient(ByteBuf buffer, BedrockPacketHelper helper) {
        ItemDescriptorType type = TYPES[buffer.readUnsignedByte()];

        ItemDescriptor descriptor;
        switch (type) {
            case DEFAULT:
                int itemId = buffer.readShortLE();
                int auxValue = itemId != 0 ? buffer.readShortLE() : 0;
                descriptor = new DefaultDescriptor(itemId, auxValue);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(helper.readString(buffer), buffer.readUnsignedByte());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(helper.readString(buffer));
                break;
            case DEFERRED:
                descriptor = new DeferredDescriptor(helper.readString(buffer), buffer.readUnsignedShortLE());
                break;
            default:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
        }

        return new ItemDescriptorWithCount(descriptor, VarInts.readInt(buffer));
    }

    @Override
    protected void writeIngredient(ByteBuf buffer, BedrockPacketHelper helper, ItemDescriptorWithCount ingredient) {
        ItemDescriptorType type = ingredient.getDescriptor().getType();
        buffer.writeByte(type.ordinal());

        switch (type) {
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) ingredient.getDescriptor();
                buffer.writeShortLE(defaultDescriptor.getItemId());
                if (defaultDescriptor.getItemId() != 0) {
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
