package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CodeBuilderPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBuilderSerializer_v407 implements BedrockPacketSerializer<CodeBuilderPacket> {
    public static final CodeBuilderSerializer_v407 INSTANCE = new CodeBuilderSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderPacket packet) {
        helper.writeString(buffer, packet.getUrl());
        buffer.writeBoolean(packet.isOpening());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderPacket packet) {
        packet.setUrl(helper.readString(buffer));
        packet.setOpening(buffer.readBoolean());
    }
}
