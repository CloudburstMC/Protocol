package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.EducationSettingsPacket;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationSettingsSerializer_v407 implements BedrockPacketSerializer<EducationSettingsPacket> {

    public static final EducationSettingsSerializer_v407 INSTANCE = new EducationSettingsSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        helper.writeString(buffer, packet.getCodeBuilderUri());
        helper.writeString(buffer, packet.getCodeBuilderTitle());
        buffer.writeBoolean(packet.isCanResizeCodeBuilder());
        helper.writeOptional(buffer, Optional::isPresent, packet.getOverrideUri(),
                (byteBuf, optional) -> helper.writeString(byteBuf, optional.get()));
        buffer.writeBoolean(packet.isQuizAttached());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setCodeBuilderTitle(helper.readString(buffer));
        packet.setCanResizeCodeBuilder(buffer.readBoolean());
        packet.setOverrideUri(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(helper.readString(byteBuf))));
        packet.setQuizAttached(buffer.readBoolean());
    }
}
