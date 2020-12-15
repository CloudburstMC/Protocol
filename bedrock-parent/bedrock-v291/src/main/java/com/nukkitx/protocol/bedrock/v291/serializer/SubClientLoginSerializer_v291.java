package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SubClientLoginPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubClientLoginSerializer_v291 implements BedrockPacketSerializer<SubClientLoginPacket> {
    public static final SubClientLoginSerializer_v291 INSTANCE = new SubClientLoginSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubClientLoginPacket packet) {
        AsciiString chainData = packet.getChainData();
        AsciiString skinData = packet.getSkinData();
        VarInts.writeUnsignedInt(buffer, chainData.length() + skinData.length() + 8);
        helper.writeLEAsciiString(buffer, chainData);
        helper.writeLEAsciiString(buffer, skinData);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubClientLoginPacket packet) {
        ByteBuf jwt = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        packet.setChainData(helper.readLEAsciiString(jwt));
        packet.setSkinData(helper.readLEAsciiString(jwt));
    }
}
