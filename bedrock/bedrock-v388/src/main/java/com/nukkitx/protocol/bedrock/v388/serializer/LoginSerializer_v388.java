package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSerializer_v388 implements PacketSerializer<LoginPacket> {
    public static final LoginSerializer_v388 INSTANCE = new LoginSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, LoginPacket packet) {
        buffer.writeInt(packet.getProtocolVersion());

        AsciiString chainData = packet.getChainData();
        AsciiString skinData = packet.getSkinData();

        VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);

        BedrockUtils.writeLEAsciiString(buffer, chainData);
        BedrockUtils.writeLEAsciiString(buffer, skinData);
    }

    @Override
    public void deserialize(ByteBuf buffer, LoginPacket packet) {
        packet.setProtocolVersion(buffer.readInt());

        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer)); // Get the JWT.
        packet.setChainData(BedrockUtils.readLEAsciiString(jwt));
        packet.setSkinData(BedrockUtils.readLEAsciiString(jwt));
    }
}
