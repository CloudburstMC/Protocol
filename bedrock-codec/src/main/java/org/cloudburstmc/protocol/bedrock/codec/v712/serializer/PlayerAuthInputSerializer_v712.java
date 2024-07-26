package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v662.serializer.PlayerAuthInputSerializer_v662;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.LegacySetItemSlotData;
import org.cloudburstmc.protocol.common.util.VarInts;

public class PlayerAuthInputSerializer_v712 extends PlayerAuthInputSerializer_v662 {
    public static final PlayerAuthInputSerializer_v712 INSTANCE = new PlayerAuthInputSerializer_v712();

    @Override
    protected void writeItemUseTransaction(ByteBuf buffer, BedrockCodecHelper helper, ItemUseTransaction transaction) {
        int legacyRequestId = transaction.getLegacyRequestId();
        VarInts.writeInt(buffer, legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.writeArray(buffer, transaction.getLegacySlots(), (buf, packetHelper, data) -> {
                buf.writeByte(data.getContainerId());
                packetHelper.writeByteArray(buf, data.getSlots());
            });
        }

        helper.writeInventoryActions(buffer, transaction.getActions(), transaction.isUsingNetIds());
        VarInts.writeUnsignedInt(buffer, transaction.getActionType());
        VarInts.writeUnsignedInt(buffer, transaction.getTriggerType().ordinal());
        helper.writeBlockPosition(buffer, transaction.getBlockPosition());
        VarInts.writeInt(buffer, transaction.getBlockFace());
        VarInts.writeInt(buffer, transaction.getHotbarSlot());
        helper.writeItem(buffer, transaction.getItemInHand());
        helper.writeVector3f(buffer, transaction.getPlayerPosition());
        helper.writeVector3f(buffer, transaction.getClickPosition());
        VarInts.writeUnsignedInt(buffer, transaction.getBlockDefinition().getRuntimeId());
        VarInts.writeUnsignedInt(buffer, transaction.getClientInteractPrediction().ordinal());
    }

    @Override
    protected ItemUseTransaction readItemUseTransaction(ByteBuf buffer, BedrockCodecHelper helper) {
        ItemUseTransaction itemTransaction = new ItemUseTransaction();

        int legacyRequestId = VarInts.readInt(buffer);
        itemTransaction.setLegacyRequestId(legacyRequestId);

        if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
            helper.readArray(buffer, itemTransaction.getLegacySlots(), (buf, packetHelper) -> {
                byte containerId = buf.readByte();
                byte[] slots = packetHelper.readByteArray(buf, 89);
                return new LegacySetItemSlotData(containerId, slots);
            });
        }

        boolean hasNetIds = helper.readInventoryActions(buffer, itemTransaction.getActions());
        itemTransaction.setActionType(VarInts.readUnsignedInt(buffer));
        itemTransaction.setTriggerType(ItemUseTransaction.TriggerType.values()[VarInts.readUnsignedInt(buffer)]);
        itemTransaction.setBlockPosition(helper.readBlockPosition(buffer));
        itemTransaction.setBlockFace(VarInts.readInt(buffer));
        itemTransaction.setHotbarSlot(VarInts.readInt(buffer));
        itemTransaction.setItemInHand(helper.readItem(buffer));
        itemTransaction.setPlayerPosition(helper.readVector3f(buffer));
        itemTransaction.setClickPosition(helper.readVector3f(buffer));
        itemTransaction.setBlockDefinition(helper.getBlockDefinitions().getDefinition(VarInts.readUnsignedInt(buffer)));
        itemTransaction.setClientInteractPrediction(ItemUseTransaction.PredictedResult.values()[VarInts.readUnsignedInt(buffer)]);
        return itemTransaction;
    }
}