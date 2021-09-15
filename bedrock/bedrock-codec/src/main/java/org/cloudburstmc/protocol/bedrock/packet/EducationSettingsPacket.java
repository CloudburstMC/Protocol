package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EducationSettingsPacket implements BedrockPacket {
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
    private boolean entityCapabilities;
    private String overrideUri;
    private boolean quizAttached;
    private boolean externalLinkSettings;

    private boolean optionalEntityCapabilities;
    private boolean optionalOverrideUri;
    private boolean optionalExternalLinkSettings;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EDUCATION_SETTINGS;
    }
}
