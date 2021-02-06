package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData;
import com.nukkitx.protocol.bedrock.data.PlayerBlockActionData;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.v419.serializer.PlayerAuthInputSerializer_v419;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v428 extends PlayerAuthInputSerializer_v419 {

    public static final PlayerAuthInputSerializer_v428 INSTANCE = new PlayerAuthInputSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        super.serialize(buffer, helper, packet);

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {

        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {

        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            VarInts.writeInt(buffer, packet.getPlayerActions().size());
            for (PlayerBlockActionData actionData : packet.getPlayerActions()) {
                writePlayerBlockActionData(buffer, helper, actionData);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        super.deserialize(buffer, helper, packet);

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {

        }

        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {

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
                helper.writeBlockPosition(buffer, actionData.getBlockPosition());
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
                actionData.setBlockPosition(helper.readBlockPosition(buffer));
                actionData.setFace(VarInts.readInt(buffer));
        }
        return actionData;
    }
}
