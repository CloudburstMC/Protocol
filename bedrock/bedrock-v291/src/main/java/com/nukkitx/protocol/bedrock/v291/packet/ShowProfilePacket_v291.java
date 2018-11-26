package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ShowProfilePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ShowProfilePacket_v291 extends ShowProfilePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, xuid);
    }

    @Override
    public void decode(ByteBuf buffer) {
        xuid = BedrockUtils.readString(buffer);
    }
}
