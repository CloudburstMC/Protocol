package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData;
import com.nukkitx.protocol.bedrock.data.PlayerBlockActionData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseTransaction;
import com.nukkitx.protocol.bedrock.data.inventory.LegacySetItemSlotData;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.v419.serializer.PlayerAuthInputSerializer_v419;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v428 extends PlayerAuthInputSerializer_v419 {

    public static final PlayerAuthInputSerializer_v428 INSTANCE = new PlayerAuthInputSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet, BedrockSession session) {
        super.serialize(buffer, helper, packet);

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
            //TODO use inventory transaction packet serialization?
            ItemUseTransaction transaction = packet.getItemUseTransaction();
            int legacyRequestId = transaction.getLegacyRequestId();
            VarInts.writeInt(buffer, legacyRequestId);

            if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                helper.writeArray(buffer, transaction.getLegacySlots(), (buf, packetHelper, data) -> {
                    buf.writeByte(data.getContainerId());
                    packetHelper.writeByteArray(buf, data.getSlots());
                });
            }

            helper.writeInventoryActions(buffer, session, transaction.getActions(), transaction.isUsingNetIds());
            VarInts.writeUnsignedInt(buffer, transaction.getActionType());
            helper.writeBlockPosition(buffer, transaction.getBlockPosition());
            VarInts.writeInt(buffer, transaction.getBlockFace());
            VarInts.writeInt(buffer, transaction.getHotbarSlot());
            helper.writeItem(buffer, transaction.getItemInHand(), session);
            helper.writeVector3f(buffer, transaction.getPlayerPosition());
            helper.writeVector3f(buffer, transaction.getClickPosition());
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
            helper.writeItemStackRequest(buffer, session, packet.getItemStackRequest());
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            VarInts.writeInt(buffer, packet.getPlayerActions().size());
            for (PlayerBlockActionData actionData : packet.getPlayerActions()) {
                writePlayerBlockActionData(buffer, helper, actionData);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet);

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
            ItemUseTransaction itemTransaction = new ItemUseTransaction();

            int legacyRequestId = VarInts.readInt(buffer);
            itemTransaction.setLegacyRequestId(legacyRequestId);

            if (legacyRequestId < -1 && (legacyRequestId & 1) == 0) {
                helper.readArray(buffer, itemTransaction.getLegacySlots(), (buf, packetHelper) -> {
                    byte containerId = buf.readByte();
                    byte[] slots = packetHelper.readByteArray(buf);
                    return new LegacySetItemSlotData(containerId, slots);
                });
            }

            boolean hasNetIds = helper.readInventoryActions(buffer, session, itemTransaction.getActions());
            itemTransaction.setActionType(VarInts.readUnsignedInt(buffer));
            itemTransaction.setBlockPosition(helper.readBlockPosition(buffer));
            itemTransaction.setBlockFace(VarInts.readInt(buffer));
            itemTransaction.setHotbarSlot(VarInts.readInt(buffer));
            itemTransaction.setItemInHand(helper.readItem(buffer, session));
            itemTransaction.setPlayerPosition(helper.readVector3f(buffer));
            itemTransaction.setClickPosition(helper.readVector3f(buffer));
            itemTransaction.setBlockRuntimeId(VarInts.readUnsignedInt(buffer));
            packet.setItemUseTransaction(itemTransaction);
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
            packet.setItemStackRequest(helper.readItemStackRequest(buffer, session));
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            int arraySize = VarInts.readInt(buffer);
            for (int i = 0; i < arraySize; i++) {
                packet.getPlayerActions().add(readPlayerBlockActionData(buffer, helper));
            }
        }
    }

    protected void writePlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper, PlayerBlockActionData actionData) {
        VarInts.writeInt(buffer, actionData.getAction().ordinal());
        switch (actionData.getAction()) {
            case START_BREAK:
            case ABORT_BREAK:
            case CONTINUE_BREAK:
            case BLOCK_PREDICT_DESTROY:
            case BLOCK_CONTINUE_DESTROY:
                helper.writeVector3i(buffer, actionData.getBlockPosition());
                VarInts.writeInt(buffer, actionData.getFace());
        }
    }

    protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper) {
        PlayerBlockActionData actionData = new PlayerBlockActionData();
        actionData.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
        switch (actionData.getAction()) {
            case START_BREAK:
            case ABORT_BREAK:
            case CONTINUE_BREAK:
            case BLOCK_PREDICT_DESTROY:
            case BLOCK_CONTINUE_DESTROY:
                actionData.setBlockPosition(helper.readVector3i(buffer));
                actionData.setFace(VarInts.readInt(buffer));
        }
        return actionData;
    }
}
