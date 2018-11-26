package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class TransferPacket_v291 extends TransferPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, address);
        buffer.writeShortLE(port);
    }

    @Override
    public void decode(ByteBuf buffer) {
        address = BedrockUtils.readString(buffer);
        port = buffer.readShortLE();
    }
}
