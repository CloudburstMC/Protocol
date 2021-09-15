package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.EducationSettingsSerializer_v407;
import org.cloudburstmc.protocol.bedrock.packet.EducationSettingsPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationSettingsSerializer_v465 extends EducationSettingsSerializer_v407 {
    public static final EducationSettingsSerializer_v465 INSTANCE = new EducationSettingsSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        helper.writeString(buffer, packet.getCodeBuilderUri());
        helper.writeString(buffer, packet.getCodeBuilderTitle());
        buffer.writeBoolean(packet.isCanResizeCodeBuilder());
        buffer.writeBoolean(packet.isDisableLegacyTitle());
        helper.writeString(buffer, packet.getPostProcessFilter());
        helper.writeString(buffer, packet.getScreenshotBorderPath());
        buffer.writeBoolean(packet.isOptionalEntityCapabilities());
        buffer.writeBoolean(packet.isOptionalOverrideUri());
        buffer.writeBoolean(packet.isQuizAttached());
        buffer.writeBoolean(packet.isOptionalExternalLinkSettings());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setCodeBuilderTitle(helper.readString(buffer));
        packet.setCanResizeCodeBuilder(buffer.readBoolean());
        packet.setDisableLegacyTitle(buffer.readBoolean());
        packet.setPostProcessFilter(helper.readString(buffer));
        packet.setScreenshotBorderPath(helper.readString(buffer));
        packet.setOptionalEntityCapabilities(buffer.readBoolean());
        packet.setOptionalOverrideUri(buffer.readBoolean());
        packet.setQuizAttached(buffer.readBoolean());
        packet.setOptionalExternalLinkSettings(buffer.readBoolean());
    }
}
