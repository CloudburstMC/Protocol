package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransactionSerializer_v291 implements BedrockPacketSerializer<InventoryTransactionPacket> {
    public static final InventoryTransactionSerializer_v291 INSTANCE = new InventoryTransactionSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        TransactionType transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        helper.writeArray(buffer, packet.getActions(), (buf, action) -> this.writeAction(buf, helper, action, session));

        switch (transactionType) {
            case ITEM_USE:
                this.writeItemUse(buffer, helper, packet, session);
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

        helper.readArray(buffer, packet.getActions(), buf -> this.readAction(buf, helper, session));

        switch (transactionType) {
            case ITEM_USE:
                this.readItemUse(buffer, helper, packet, session);
                break;
            case ITEM_USE_ON_ENTITY:
                this.readItemUseOnEntity(buffer, helper, packet, session);
                break;
            case ITEM_RELEASE:
                this.readItemRelease(buffer, helper, packet, session);
                break;
        }
    }

    public InventoryActionData readAction(ByteBuf buffer, BedrockPacketHelper helper, BedrockSession session) {
        InventorySource source = this.readSource(buffer);
        int slot = VarInts.readUnsignedInt(buffer);
        ItemData fromItem = helper.readItem(buffer, session);
        ItemData toItem = helper.readItem(buffer, session);

        return new InventoryActionData(source, slot, fromItem, toItem);
    }

    public void writeAction(ByteBuf buffer, BedrockPacketHelper helper, InventoryActionData action, BedrockSession session) {
        requireNonNull(action, "InventoryActionData was null");

        this.writeSource(buffer, action.getSource());
        VarInts.writeUnsignedInt(buffer, action.getSlot());
        helper.writeItem(buffer, action.getFromItem(), session);
        helper.writeItem(buffer, action.getToItem(), session);
    }

    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer));

        switch (type) {
            case CONTAINER:
                int containerId = VarInts.readInt(buffer);
                return InventorySource.fromContainerWindowId(containerId);
            case GLOBAL:
                return InventorySource.fromGlobalInventory();
            case WORLD_INTERACTION:
                InventorySource.Flag flag = InventorySource.Flag.values()[VarInts.readUnsignedInt(buffer)];
                return InventorySource.fromWorldInteraction(flag);
            case CREATIVE:
                return InventorySource.fromCreativeInventory();
            case NON_IMPLEMENTED_TODO:
                containerId = VarInts.readInt(buffer);
                return InventorySource.fromNonImplementedTodo(containerId);
            default:
                return InventorySource.fromInvalid();
        }
    }

    public void writeSource(ByteBuf buffer, InventorySource inventorySource) {
        requireNonNull(inventorySource, "InventorySource was null");

        VarInts.writeUnsignedInt(buffer, inventorySource.getType().id());

        switch (inventorySource.getType()) {
            case CONTAINER:
            case UNTRACKED_INTERACTION_UI:
            case NON_IMPLEMENTED_TODO:
                VarInts.writeInt(buffer, inventorySource.getContainerId());
                break;
            case WORLD_INTERACTION:
                VarInts.writeUnsignedInt(buffer, inventorySource.getFlag().ordinal());
                break;
        }
    }

    public void readItemUse(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        packet.setActionType(VarInts.readUnsignedInt(buffer));
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setBlockFace(VarInts.readInt(buffer));
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setItemInHand(helper.readItem(buffer, session));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setClickPosition(helper.readVector3f(buffer));
    }

    public void writeItemUse(ByteBuf buffer, BedrockPacketHelper helper, InventoryTransactionPacket packet, BedrockSession session) {
        VarInts.writeUnsignedInt(buffer, packet.getActionType());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getBlockFace());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        helper.writeItem(buffer, packet.getItemInHand(), session);
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector3f(buffer, packet.getClickPosition());
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
