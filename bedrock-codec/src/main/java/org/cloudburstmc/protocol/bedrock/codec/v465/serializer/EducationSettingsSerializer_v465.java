package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.EducationSettingsSerializer_v407;
import org.cloudburstmc.protocol.bedrock.packet.EducationSettingsPacket;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
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
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getEntityCapabilities(),
                (byteBuf, optional) -> byteBuf.writeBoolean(optional.getAsBoolean()));
        helper.writeOptional(buffer, Optional::isPresent, packet.getOverrideUri(),
                (byteBuf, optional) -> helper.writeString(byteBuf, optional.get()));
        buffer.writeBoolean(packet.isQuizAttached());
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getExternalLinkSettings(),
                (byteBuf, optional) -> byteBuf.writeBoolean(optional.getAsBoolean()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setCodeBuilderTitle(helper.readString(buffer));
        packet.setCanResizeCodeBuilder(buffer.readBoolean());
        packet.setDisableLegacyTitle(buffer.readBoolean());
        packet.setPostProcessFilter(helper.readString(buffer));
        packet.setScreenshotBorderPath(helper.readString(buffer));
        packet.setEntityCapabilities(helper.readOptional(buffer, OptionalBoolean.empty(),
                byteBuf -> OptionalBoolean.of(buffer.readBoolean())));
        packet.setOverrideUri(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(helper.readString(byteBuf))));
        packet.setQuizAttached(buffer.readBoolean());
        packet.setExternalLinkSettings(helper.readOptional(buffer, OptionalBoolean.empty(),
                byteBuf -> OptionalBoolean.of(buffer.readBoolean())));
    }
}
