package com.nukkitx.protocol.bedrock.v422.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.v419.serializer.ItemStackResponseSerializer_v419;
import io.netty.buffer.ByteBuf;

public class ItemStackResponseSerializer_v422 extends ItemStackResponseSerializer_v419 {

    public static final ItemStackResponseSerializer_v422 INSTANCE = new ItemStackResponseSerializer_v422();

    @Override
    protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
        return new ItemStackResponsePacket.ItemEntry(
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer),
                0);
    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        helper.writeString(buffer, itemEntry.getCustomName());
    }
}
