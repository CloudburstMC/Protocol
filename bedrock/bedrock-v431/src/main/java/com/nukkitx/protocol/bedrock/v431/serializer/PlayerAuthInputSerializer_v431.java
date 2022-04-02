package com.nukkitx.protocol.bedrock.v431.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PlayerBlockActionData;
import com.nukkitx.protocol.bedrock.v428.serializer.PlayerAuthInputSerializer_v428;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v431 extends PlayerAuthInputSerializer_v428 {

    public static final PlayerAuthInputSerializer_v431 INSTANCE = new PlayerAuthInputSerializer_v431();

    @Override
    protected void writePlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper, PlayerBlockActionData actionData) {
        super.writePlayerBlockActionData(buffer, helper, actionData);
        // PlayerActionType.STOP_BREAK - NOOP
    }

    @Override
    protected PlayerBlockActionData readPlayerBlockActionData(ByteBuf buffer, BedrockPacketHelper helper) {
        // PlayerActionType.STOP_BREAK - NOOP
        return super.readPlayerBlockActionData(buffer, helper);
    }
}
