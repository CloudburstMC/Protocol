package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CodeBuilderPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBuilderSerializer_v407 implements BedrockPacketSerializer<CodeBuilderPacket> {
    public static final CodeBuilderSerializer_v407 INSTANCE = new CodeBuilderSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderPacket packet) {
        helper.writeString(buffer, packet.getUrl());
        buffer.writeBoolean(packet.isOpening());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderPacket packet) {
        packet.setUrl(helper.readString(buffer));
        packet.setOpening(buffer.readBoolean());
    }
}
