package org.cloudburstmc.protocol.bedrock.codec.v428.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.PlayerAuthInputSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData;
import org.cloudburstmc.protocol.bedrock.data.PlayerBlockActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.LegacySetItemSlotData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v428 extends PlayerAuthInputSerializer_v419 {

    public static final PlayerAuthInputSerializer_v428 INSTANCE = new PlayerAuthInputSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
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

            helper.writeInventoryActions(buffer, transaction.getActions(), transaction.isUsingNetIds());
            VarInts.writeUnsignedInt(buffer, transaction.getActionType());
            helper.writeBlockPosition(buffer, transaction.getBlockPosition());
            VarInts.writeInt(buffer, transaction.getBlockFace());
            VarInts.writeInt(buffer, transaction.getHotbarSlot());
            helper.writeItem(buffer, transaction.getItemInHand());
            helper.writeVector3f(buffer, transaction.getPlayerPosition());
            helper.writeVector3f(buffer, transaction.getClickPosition());
            VarInts.writeUnsignedInt(buffer, transaction.getBlockDefinition().getRuntimeId());
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
            helper.writeItemStackRequest(buffer, packet.getItemStackRequest());
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            VarInts.writeInt(buffer, packet.getPlayerActions().size());
            for (PlayerBlockActionData actionData : packet.getPlayerActions()) {
                writePlayerBlockActionData(buffer, helper, actionData);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        super.deserialize(buffer, helper, packet);

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
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
            itemTransaction.setBlockPosition(helper.readBlockPosition(buffer));
            itemTransaction.setBlockFace(VarInts.readInt(buffer));
            itemTransaction.setHotbarSlot(VarInts.readInt(buffer));
            itemTransaction.setItemInHand(helper.readItem(buffer));
            itemTransaction.setPlayerPosition(helper.readVector3f(buffer));
            itemTransaction.setClickPosition(helper.readVector3f(buffer));
            itemTransaction.setBlockDefinition(helper.getBlockDefinitions().getDefinition(VarInts.readUnsignedInt(buffer)));
            packet.setItemUseTransaction(itemTransaction);
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
            packet.setItemStackRequest(helper.readItemStackRequest(buffer));
        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            helper.readArray(buffer, packet.getPlayerActions(), VarInts::readInt, this::readPlayerBlockActionData, 32); // 32 is more than enough
        }
    }

    protected void writePlayerBlockActionData(ByteBuf buffer, BedrockCodecHelper helper, PlayerBlockActionData actionData) {
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

    protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockCodecHelper helper) {
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
