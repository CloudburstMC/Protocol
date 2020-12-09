package com.nukkitx.protocol.bedrock.v422;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.v419.BedrockPacketHelper_v419;
import io.netty.buffer.ByteBuf;

public class BedrockPacketHelper_v422 extends BedrockPacketHelper_v419 {

    public static final BedrockPacketHelper_v422 INSTANCE = new BedrockPacketHelper_v422();

    @Override
    public ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockPacketHelper helper) {
        return new ItemStackResponsePacket.ItemEntry(
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer));
    }

    @Override
    public void writeItemEntry(ByteBuf buffer, BedrockPacketHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        helper.writeString(buffer, itemEntry.getCustomName());
    }
}
