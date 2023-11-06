package org.cloudburstmc.protocol.bedrock.codec.v630.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryLayout;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabLeft;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabRight;
import org.cloudburstmc.protocol.bedrock.packet.SetPlayerInventoryOptionsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class SetPlayerInventoryOptionsSerializer_v360 implements BedrockPacketSerializer<SetPlayerInventoryOptionsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerInventoryOptionsPacket packet) {
        VarInts.writeInt(buffer, packet.getLeftTab().ordinal());
        VarInts.writeInt(buffer, packet.getRightTab().ordinal());
        buffer.writeBoolean(packet.isFiltering());
        VarInts.writeInt(buffer, packet.getLayout().ordinal());
        VarInts.writeInt(buffer, packet.getCraftingLayout().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerInventoryOptionsPacket packet) {
        packet.setLeftTab(InventoryTabLeft.VALUES[VarInts.readInt(buffer)]);
        packet.setRightTab(InventoryTabRight.VALUES[VarInts.readInt(buffer)]);
        packet.setFiltering(buffer.readBoolean());
        packet.setLayout(InventoryLayout.VALUES[VarInts.readInt(buffer)]);
        packet.setCraftingLayout(InventoryLayout.VALUES[VarInts.readInt(buffer)]);
    }
}