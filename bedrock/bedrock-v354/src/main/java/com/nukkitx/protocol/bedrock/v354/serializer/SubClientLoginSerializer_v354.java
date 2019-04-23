package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SubClientLoginPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubClientLoginSerializer_v354 implements PacketSerializer<SubClientLoginPacket> {
    public static final SubClientLoginSerializer_v354 INSTANCE = new SubClientLoginSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, SubClientLoginPacket packet) {
        AsciiString chainData = packet.getChainData();
        AsciiString skinData = packet.getSkinData();
        VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);
        BedrockUtils.writeLEAsciiString(buffer, chainData);
        BedrockUtils.writeLEAsciiString(buffer, skinData);
    }

    @Override
    public void deserialize(ByteBuf buffer, SubClientLoginPacket packet) {
        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        packet.setChainData(BedrockUtils.readLEAsciiString(jwt));
        packet.setSkinData(BedrockUtils.readLEAsciiString(jwt));
    }
}
