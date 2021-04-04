package com.nukkitx.protocol.bedrock.v431.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.data.PlayerBlockActionData;
import com.nukkitx.protocol.bedrock.v428.serializer.PlayerAuthInputSerializer_v428;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v431 extends PlayerAuthInputSerializer_v428 {

    @Override
    protected void writePlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper, PlayerBlockActionData actionData) {
        super.writePlayerBlockActionData(buffer, helper, actionData);
        if (actionData.getAction() == PlayerActionType.STOP_BREAK) {
            helper.writeVector3i(buffer, actionData.getBlockPosition());
            VarInts.writeInt(buffer, actionData.getFace());
        }
    }

    @Override
    protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper) {
        PlayerBlockActionData data = super.readPlayerBlockActionData(buffer, helper);

        if (data.getAction() == PlayerActionType.STOP_BREAK) {
            data.setBlockPosition(helper.readVector3i(buffer));
            data.setFace(VarInts.readInt(buffer));
        }
        return data;
    }
}
