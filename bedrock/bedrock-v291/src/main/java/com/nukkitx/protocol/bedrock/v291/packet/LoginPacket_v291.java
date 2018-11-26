package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class LoginPacket_v291 extends LoginPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeInt(protocolVersion);

        VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);

        BedrockUtils.writeLEAsciiString(buffer, chainData);
        BedrockUtils.writeLEAsciiString(buffer, skinData);
    }

    @Override
    public void decode(ByteBuf buffer) {
        protocolVersion = buffer.readInt();

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.
        chainData = BedrockUtils.readLEAsciiString(jwt);
        skinData = BedrockUtils.readLEAsciiString(jwt);
    }
}
