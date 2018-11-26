package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SubClientLoginPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SubClientLoginPacket_v291 extends SubClientLoginPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);
        BedrockUtils.writeLEAsciiString(buffer, chainData);
        BedrockUtils.writeLEAsciiString(buffer, skinData);
    }

    @Override
    public void decode(ByteBuf buffer) {
        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        chainData = BedrockUtils.readLEAsciiString(jwt);
        skinData = BedrockUtils.readLEAsciiString(jwt);
    }
}
