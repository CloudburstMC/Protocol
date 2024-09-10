package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
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
    private OptionalBoolean entityCapabilities;
    private Optional<String> overrideUri;
    private boolean quizAttached;
    private OptionalBoolean externalLinkSettings;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EDUCATION_SETTINGS;
    }

    @Override
    public EducationSettingsPacket clone() {
        try {
            return (EducationSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

