package org.cloudburstmc.protocol.bedrock.codec.v431.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.serializer.PlayerAuthInputSerializer_v428;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;
import org.cloudburstmc.protocol.bedrock.data.PlayerBlockActionData;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v431 extends PlayerAuthInputSerializer_v428 {

    public static final PlayerAuthInputSerializer_v431 INSTANCE = new PlayerAuthInputSerializer_v431();

    @Override
    protected void writePlayerBlockActionData(ByteBuf buffer, BedrockCodecHelper helper, PlayerBlockActionData actionData) {
        super.writePlayerBlockActionData(buffer, helper, actionData);
        if (actionData.getAction() == PlayerActionType.STOP_BREAK) {
            helper.writeVector3i(buffer, actionData.getBlockPosition());
            VarInts.writeInt(buffer, actionData.getFace());
        }
    }

    @Override
    protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockCodecHelper helper) {
        PlayerBlockActionData data = super.readPlayerBlockActionData(buffer, helper);

        if (data.getAction() == PlayerActionType.STOP_BREAK) {
            data.setBlockPosition(helper.readVector3i(buffer));
            data.setFace(VarInts.readInt(buffer));
        }
        return data;
    }
}
