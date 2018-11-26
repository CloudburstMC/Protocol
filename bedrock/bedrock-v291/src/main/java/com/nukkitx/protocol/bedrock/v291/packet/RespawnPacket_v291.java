package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class RespawnPacket_v291 extends RespawnPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector3f(buffer, position);
    }

    @Override
    public void decode(ByteBuf buffer) {
        position = BedrockUtils.readVector3f(buffer);
    }
}
