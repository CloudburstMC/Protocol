package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryTransactionSerializer_v361 implements PacketSerializer<InventoryTransactionPacket> {
    public static final InventoryTransactionSerializer_v361 INSTANCE = new InventoryTransactionSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, InventoryTransactionPacket packet) {
        Type transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        BedrockUtils.writeArray(buffer, packet.getActions(), BedrockUtils::writeInventoryAction);

        switch (transactionType) {
            case ITEM_USE:
                VarInts.writeUnsignedInt(buffer, packet.getActionType());
                BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
                VarInts.writeInt(buffer, packet.getFace());
                VarInts.writeInt(buffer, packet.getHotbarSlot());
                BedrockUtils.writeItemData(buffer, packet.getItemInHand());
                BedrockUtils.writeVector3f(buffer, packet.getPlayerPosition());
                BedrockUtils.writeVector3f(buffer, packet.getClickPosition());
                VarInts.writeUnsignedInt(buffer, packet.getBlockRuntimeId());
                break;
            case ITEM_USE_ON_ENTITY:
                VarInts.writeUnsignedInt(buffer, packet.getRuntimeEntityId());
                VarInts.writeUnsignedInt(buffer, packet.getActionType());
                VarInts.writeInt(buffer, packet.getHotbarSlot());
                BedrockUtils.writeItemData(buffer, packet.getItemInHand());
                BedrockUtils.writeVector3f(buffer, packet.getPlayerPosition());
                BedrockUtils.writeVector3f(buffer, packet.getClickPosition());
                break;
            case ITEM_RELEASE:
                VarInts.writeUnsignedInt(buffer, packet.getActionType());
                VarInts.writeInt(buffer, packet.getHotbarSlot());
                BedrockUtils.writeItemData(buffer, packet.getItemInHand());
                BedrockUtils.writeVector3f(buffer, packet.getHeadPosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, InventoryTransactionPacket packet) {
        Type transactionType = Type.values()[VarInts.readUnsignedInt(buffer)];
        packet.setTransactionType(transactionType);

        BedrockUtils.readArray(buffer, packet.getActions(), BedrockUtils::readInventoryAction);

        switch (transactionType) {
            case ITEM_USE:
                packet.setActionType(VarInts.readUnsignedInt(buffer));
                packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
                packet.setFace(VarInts.readInt(buffer));
                packet.setHotbarSlot(VarInts.readInt(buffer));
                packet.setItemInHand(BedrockUtils.readItemData(buffer));
                packet.setPlayerPosition(BedrockUtils.readVector3f(buffer));
                packet.setClickPosition(BedrockUtils.readVector3f(buffer));
                packet.setBlockRuntimeId(VarInts.readUnsignedInt(buffer));
                break;
            case ITEM_USE_ON_ENTITY:
                packet.setRuntimeEntityId(VarInts.readUnsignedInt(buffer));
                packet.setActionType(VarInts.readUnsignedInt(buffer));
                packet.setHotbarSlot(VarInts.readInt(buffer));
                packet.setItemInHand(BedrockUtils.readItemData(buffer));
                packet.setPlayerPosition(BedrockUtils.readVector3f(buffer));
                packet.setClickPosition(BedrockUtils.readVector3f(buffer));
                break;
            case ITEM_RELEASE:
                packet.setActionType(VarInts.readUnsignedInt(buffer));
                packet.setHotbarSlot(VarInts.readInt(buffer));
                packet.setItemInHand(BedrockUtils.readItemData(buffer));
                packet.setHeadPosition(BedrockUtils.readVector3f(buffer));
        }
    }
}
