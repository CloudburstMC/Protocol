package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.WSConnectPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class WSConnectPacket_v291 extends WSConnectPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, address);
    }

    @Override
    public void decode(ByteBuf buffer) {
        address = BedrockUtils.readString(buffer);
    }
}
