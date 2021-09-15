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
        helper.writeOptional(buffer, packet.isOptionalEntityCapabilities(), packet.isEntityCapabilities(), ByteBuf::writeBoolean);
        helper.writeOptional(buffer, packet.isOptionalOverrideUri(), packet.getOverrideUri(), helper::writeString);
        buffer.writeBoolean(packet.isQuizAttached());
        helper.writeOptional(buffer, packet.isOptionalExternalLinkSettings(), packet.isExternalLinkSettings(), ByteBuf::writeBoolean);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setCodeBuilderTitle(helper.readString(buffer));
        packet.setCanResizeCodeBuilder(buffer.readBoolean());
        packet.setDisableLegacyTitle(buffer.readBoolean());
        packet.setPostProcessFilter(helper.readString(buffer));
        packet.setScreenshotBorderPath(helper.readString(buffer));
        helper.readOptional(buffer, buf -> {
            packet.setOptionalEntityCapabilities(true);
            packet.setEntityCapabilities(buf.readBoolean());
        });
        helper.readOptional(buffer, buf -> {
            packet.setOptionalOverrideUri(true);
            packet.setOverrideUri(helper.readString(buf));
        });
        packet.setQuizAttached(buffer.readBoolean());
        helper.readOptional(buffer, buf -> {
            packet.setOptionalExternalLinkSettings(true);
            packet.setExternalLinkSettings(buffer.readBoolean());
        });
    }
}
