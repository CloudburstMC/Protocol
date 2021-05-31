package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.EducationSettingsPacket;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EducationSettingsSerializer_v388 implements BedrockPacketSerializer<EducationSettingsPacket> {

    public static final EducationSettingsSerializer_v388 INSTANCE = new EducationSettingsSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        helper.writeString(buffer, packet.getCodeBuilderUri());
        buffer.writeBoolean(packet.isQuizAttached());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setQuizAttached(buffer.readBoolean());
    }
}
