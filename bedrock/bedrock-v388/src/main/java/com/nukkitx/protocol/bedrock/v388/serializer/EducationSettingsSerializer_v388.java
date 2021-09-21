package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EducationSettingsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationSettingsSerializer_v388 implements BedrockPacketSerializer<EducationSettingsPacket> {

    public static final EducationSettingsSerializer_v388 INSTANCE = new EducationSettingsSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
        helper.writeString(buffer, packet.getCodeBuilderUri());
        buffer.writeBoolean(packet.isQuizAttached());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setQuizAttached(buffer.readBoolean());
    }
}
