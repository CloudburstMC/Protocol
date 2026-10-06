package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.InventoryTransactionSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.data.inventory.HandSlot;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class InventoryTransactionSerializer_v2225 extends InventoryTransactionSerializer_v2193 {

    public static final InventoryTransactionSerializer_v2225 INSTANCE = new InventoryTransactionSerializer_v2225();

    @Override
    public void readItemUseOnEntity(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setActionType(VarInts.readInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setHand(HandSlot.values()[buffer.readUnsignedByte()]); // new
        packet.setItemInHand(helper.readNetworkItemStackDescriptor(buffer));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setClickPosition(helper.readVector3f(buffer));
    }

    @Override
    public void writeItemUseOnEntity(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getActionType());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        buffer.writeByte(packet.getHand().ordinal()); // new
        helper.writeNetworkItemStackDescriptor(buffer, packet.getItemInHand());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector3f(buffer, packet.getClickPosition());
    }

    @Override
    public void readItemRelease(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        super.readItemRelease(buffer, helper, packet);
        packet.setHand(HandSlot.values()[buffer.readUnsignedByte()]);
    }

    @Override
    public void writeItemRelease(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        super.writeItemRelease(buffer, helper, packet);
        buffer.writeByte(packet.getHand().ordinal());
    }
}
