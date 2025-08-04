package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TextPacket implements BedrockPacket {
    private Type type;
    private String sourceName;
    private Component message;
    private String xuid;
    private String platformChatId = "";
    /**
     * @since v685
     */
    private Component filteredMessage = Component.empty();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TEXT;
    }

    public enum Type {
        RAW,
        CHAT,
        TRANSLATION,
        POPUP,
        JUKEBOX_POPUP,
        TIP,
        SYSTEM,
        WHISPER,
        ANNOUNCEMENT,
        WHISPER_JSON,
        JSON,
        /**
         * @since v553
         */
        ANNOUNCEMENT_JSON
    }

    @Override
    public TextPacket clone() {
        try {
            return (TextPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

