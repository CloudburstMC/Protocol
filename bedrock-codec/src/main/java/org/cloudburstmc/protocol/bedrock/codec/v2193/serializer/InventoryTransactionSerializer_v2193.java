package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.InventoryTransactionSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.*;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

public class InventoryTransactionSerializer_v2193 extends InventoryTransactionSerializer_v1001 {

    public static final InventoryTransactionSerializer_v2193 INSTANCE = new InventoryTransactionSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        int legacyRequestId = packet.getLegacyRequestId();
        VarInts.writeInt(buffer, legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            buffer.writeBoolean(true);
            helper.writeArray(buffer, packet.getLegacySlots(), (buf, packetHelper, data) -> {
                buf.writeByte(data.getContainerId());
                packetHelper.writeByteArray(buf, data.getSlots());
            });
        } else {
            buffer.writeBoolean(false);
        }

        InventoryTransactionType transactionType = packet.getTransactionType();
        VarInts.writeUnsignedInt(buffer, transactionType.ordinal());

        writeInventoryActions(buffer, helper, packet.getActions());

        switch (transactionType) {
            case ITEM_USE:
                this.writeItemUse(buffer, helper, packet);
                break;
            case ITEM_USE_ON_ENTITY:
                this.writeItemUseOnEntity(buffer, helper, packet);
                break;
            case ITEM_RELEASE:
                this.writeItemRelease(buffer, helper, packet);
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        int legacyRequestId = VarInts.readInt(buffer);
        packet.setLegacyRequestId(legacyRequestId);

        if (buffer.readBoolean()) {
            if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                helper.readArray(buffer, packet.getLegacySlots(), (buf, packetHelper) -> {
                    int containerId = buffer.readUnsignedByte();
                    byte[] slots = packetHelper.readByteArray(buf, 89); // 89 seems to be the largest slot count
                    return new LegacySetItemSlotData(containerId, slots);
                });
            }
        }

        InventoryTransactionType transactionType = InventoryTransactionType.values()[VarInts.readUnsignedInt(buffer)];
        packet.setTransactionType(transactionType);

        readInventoryActions(buffer, helper, packet.getActions());

        switch (transactionType) {
            case ITEM_USE:
                this.readItemUse(buffer, helper, packet);
                break;
            case ITEM_USE_ON_ENTITY:
                this.readItemUseOnEntity(buffer, helper, packet);
                break;
            case ITEM_RELEASE:
                this.readItemRelease(buffer, helper, packet);
                break;
        }
    }

    @Override
    public void writeItemUse(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        VarInts.writeInt(buffer, packet.getActionType());
        buffer.writeByte(packet.getTriggerType().ordinal());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        buffer.writeByte(packet.getBlockFace());
        VarInts.writeInt(buffer, packet.getHotbarSlot());
        buffer.writeByte(packet.getHand()); // new
        helper.writeNetworkItemStackDescriptor(buffer, packet.getItemInHand());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector3f(buffer, packet.getClickPosition());
        VarInts.writeUnsignedInt(buffer, packet.getBlockDefinition().getRuntimeId());
        buffer.writeByte(packet.getClientInteractPrediction().ordinal());
        buffer.writeByte(packet.getClientCooldownState());
    }

    @Override
    public void readItemUse(ByteBuf buffer, BedrockCodecHelper helper, InventoryTransactionPacket packet) {
        packet.setActionType(VarInts.readInt(buffer));
        packet.setTriggerType(ItemUseTransaction.TriggerType.values()[buffer.readUnsignedByte()]);
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setBlockFace(buffer.readUnsignedByte());
        packet.setHotbarSlot(VarInts.readInt(buffer));
        packet.setHand(buffer.readUnsignedByte()); // new
        packet.setItemInHand(helper.readNetworkItemStackDescriptor(buffer));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setClickPosition(helper.readVector3f(buffer));
        packet.setBlockDefinition(helper.getBlockDefinitions().getDefinition(VarInts.readUnsignedInt(buffer)));
        packet.setClientInteractPrediction(ItemUseTransaction.PredictedResult.values()[buffer.readUnsignedByte()]);
        packet.setClientCooldownState(buffer.readByte());
    }

    @Override
    public void readInventoryActions(ByteBuf buffer, BedrockCodecHelper helper, List<InventoryActionData> actions) {
        helper.readArray(buffer, actions, (buf, h) -> {
            InventorySource source = helper.readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = h.readNetworkItemStackDescriptor(buf);
            ItemData toItem = h.readNetworkItemStackDescriptor(buf);

            return new InventoryActionData(source, slot, fromItem, toItem);
        }, helper.getEncodingSettings().maxInventoryActionsOrRequests());
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, BedrockCodecHelper helper, List<InventoryActionData> actions) {
        helper.writeArray(buffer, actions, (buf, h, action) -> {
            helper.writeSource(buf, action.getSource());
            VarInts.writeUnsignedInt(buf, action.getSlot());
            h.writeNetworkItemStackDescriptor(buf, action.getFromItem());
            h.writeNetworkItemStackDescriptor(buf, action.getToItem());
        });
    }
}
