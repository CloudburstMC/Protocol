package org.cloudburstmc.protocol.bedrock.codec.v422.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemStackResponseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ItemStackResponseSerializer_v422 extends ItemStackResponseSerializer_v419 {

    public static final ItemStackResponseSerializer_v422 INSTANCE = new ItemStackResponseSerializer_v422();

    @Override
    protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        return new ItemStackResponsePacket.ItemEntry(
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer),
                0);
    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        helper.writeString(buffer, itemEntry.getCustomName());
    }
}
