package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v291 implements BedrockPacketSerializer<InventoryTransactionPacket> {
    public static final InventoryTransactionSerializer_v291 INSTANCE = new InventoryTransactionSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        TransactionType transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        helper.writeInventoryActions(buffer, session, packet.getActions(), false);

        switch (transactionType) {
            case ITEM_USE:
                helper.writeItemUse(buffer, packet, session);
                break;
            case ITEM_USE_ON_ENTITY:
                this.writeItemUseOnEntity(buffer, helper, packet, session);
                break;
            case ITEM_RELEASE:
                this.writeItemRelease(buffer, helper, packet, session);
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        TransactionType transactionType = TransactionType.values()[VarInts.readUnsignedInt(buffer)];
        packet.setTransactionType(transactionType);

        helper.readInventoryActions(buffer, session, packet.getActions());

        switch (transactionType) {
            case ITEM_USE:
                helper.readItemUse(buffer, packet, session);
                break;
            case ITEM_USE_ON_ENTITY:
                this.readItemUseOnEntity(buffer, helper, packet, session);
                break;
            case ITEM_RELEASE:
                this.readItemRelease(buffer, helper, packet, session);
                break;
        }
    }

    public void readItemUseOnEntity(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(helper.readItem(buffer, session));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setClickPosition(helper.readVector3f(buffer));
    }

    public void writeItemUseOnEntity(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        helper.writeItem(buffer, packet.getItemInHand(), session);
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector3f(buffer, packet.getClickPosition());
    }

    public void readItemRelease(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(helper.readItem(buffer, session));
        packet.setHeadPosition(helper.readVector3f(buffer));
    }

    public void writeItemRelease(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        helper.writeItem(buffer, packet.getItemInHand(), session);
        helper.writeVector3f(buffer, packet.getHeadPosition());
    }
}
