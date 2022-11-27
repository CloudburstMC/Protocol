package org.cloudburstmc.protocol.bedrock.codec.v422.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemStackResponseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ItemStackResponseSerializer_v422 extends ItemStackResponseSerializer_v419 {

    public static final ItemStackResponseSerializer_v422 INSTANCE = new ItemStackResponseSerializer_v422();

    @Override
    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        return new ItemStackResponseSlot(
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer),
                0);
    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponseSlot itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        helper.writeString(buffer, itemEntry.getCustomName());
    }
}
