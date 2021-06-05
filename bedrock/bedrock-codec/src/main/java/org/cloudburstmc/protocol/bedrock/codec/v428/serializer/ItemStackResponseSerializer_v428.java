package org.cloudburstmc.protocol.bedrock.codec.v428.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v422.serializer.ItemStackResponseSerializer_v422;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ItemStackResponseSerializer_v428 extends ItemStackResponseSerializer_v422 {

    public static final ItemStackResponseSerializer_v428 INSTANCE = new ItemStackResponseSerializer_v428();

    @Override
    protected ItemStackResponsePacket.ItemEntry readItemEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        return new ItemStackResponsePacket.ItemEntry(
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                VarInts.readInt(buffer),
                helper.readString(buffer),
                VarInts.readInt(buffer));
    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket.ItemEntry itemEntry) {
        super.writeItemEntry(buffer, helper, itemEntry);
        VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
    }
}
