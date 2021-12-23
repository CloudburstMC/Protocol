package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.util.OptionalBoolean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class EducationSettingsPacket extends BedrockPacket {
    private String codeBuilderUri;
    private String codeBuilderTitle;
    private boolean canResizeCodeBuilder;
    /**
     * @since v465
     */
    private boolean disableLegacyTitle;
    /**
     * @since v465
     */
    private String postProcessFilter;
    /**
     * @since v465
     */
    private String screenshotBorderPath;
    private OptionalBoolean entityCapabilities;
    private Optional<String> overrideUri;
    private boolean quizAttached;
    private OptionalBoolean externalLinkSettings;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EDUCATION_SETTINGS;
    }
}
