package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.v422.serializer.ItemStackResponseSerializer_v422;
import io.netty.buffer.ByteBuf;

public class ItemStackResponseSerializer_v428 extends ItemStackResponseSerializer_v422 {

    public static final ItemStackResponseSerializer_v428 INSTANCE = new ItemStackResponseSerializer_v428();

    @Override
    protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
        return new ItemStackResponsePacket.ItemEntry(
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer),
                VarInts.readInt(buffer));
    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
    }
}
