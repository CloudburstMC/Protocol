package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData;
import com.nukkitx.protocol.bedrock.data.PlayerBlockActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseTransaction;
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
            //TODO use inventory transaction packet serialization
            buffer.writeBoolean(packet.getItemUseTransaction().isHasNetworkIds());
            VarInts.writeUnsignedInt(buffer, packet.getItemUseTransaction().getActions().size());
            for (InventoryActionData actionData : packet.getItemUseTransaction().getActions()) {
                helper.writeInventoryAction(buffer, actionData, session, packet.getItemUseTransaction().isHasNetworkIds());
            }
            VarInts.writeUnsignedInt(buffer, packet.getItemUseTransaction().getActionType());
            helper.writeBlockPosition(buffer, packet.getItemUseTransaction().getBlockPosition());
            VarInts.writeInt(buffer, packet.getItemUseTransaction().getBlockFace());
            VarInts.writeInt(buffer, packet.getItemUseTransaction().getHotbarSlot());
            helper.writeItem(buffer, packet.getItemUseTransaction().getItemInHand(), session);
            helper.writeVector3f(buffer, packet.getItemUseTransaction().getPlayerPosition());
            helper.writeVector3f(buffer, packet.getItemUseTransaction().getClickPosition());
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
            itemTransaction.setHasNetworkIds(buffer.readBoolean());
            int actionLength = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < actionLength; i++) {
                itemTransaction.getActions().add(helper.readInventoryAction(buffer, session, itemTransaction.isHasNetworkIds()));
            }
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
