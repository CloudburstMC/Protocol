package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.PlayerInputPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class PlayerInputPacket_v291 extends PlayerInputPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector2f(buffer, inputMotion);
        buffer.writeBoolean(jumping);
        buffer.writeBoolean(sneaking);
    }

    @Override
    public void decode(ByteBuf buffer) {
        inputMotion = BedrockUtils.readVector2f(buffer);
        jumping = buffer.readBoolean();
        sneaking = buffer.readBoolean();
    }
}
