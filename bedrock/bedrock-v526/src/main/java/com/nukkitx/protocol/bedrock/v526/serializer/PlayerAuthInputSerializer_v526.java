package com.nukkitx.protocol.bedrock.v526.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.InputInteractionModel;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.v431.serializer.PlayerAuthInputSerializer_v431;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v526 extends PlayerAuthInputSerializer_v431 {

    public static final PlayerAuthInputSerializer_v526 INSTANCE = new PlayerAuthInputSerializer_v526();

    protected static final InputInteractionModel[] VALUES = InputInteractionModel.values();

    @Override
    protected void readInteractionModel(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        packet.setInputInteractionModel(VALUES[buffer.readIntLE()]);
    }

    @Override
    protected void writeInteractionModel(ByteBuf buffer, BedrockPacketHelper helper, PlayerAuthInputPacket packet) {
        buffer.writeIntLE(packet.getInputInteractionModel().ordinal());
    }
}
