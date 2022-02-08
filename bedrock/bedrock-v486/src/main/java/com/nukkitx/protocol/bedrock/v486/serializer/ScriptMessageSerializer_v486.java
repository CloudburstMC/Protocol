package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ScriptMessagePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptMessageSerializer_v486 implements BedrockPacketSerializer<ScriptMessagePacket> {

    public static final ScriptMessageSerializer_v486 INSTANCE = new ScriptMessageSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptMessagePacket packet) {
        helper.writeString(buffer, packet.getChannel());
        helper.writeString(buffer, packet.getMessage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptMessagePacket packet) {
        packet.setChannel(helper.readString(buffer));
        packet.setMessage(helper.readString(buffer));
    }
}
