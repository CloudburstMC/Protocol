package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TextPacket implements BedrockPacket {
    private Type type;
    private String sourceName;
    private CharSequence message;
    private List<String> parameters = new ObjectArrayList<>();
    private boolean needsTranslation;
    private String xuid;
    private String platformChatId = "";
    /**
     * @since v685
     */
    private CharSequence filteredMessage = "";

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

    public String getMessage() {
        return getMessage(String.class);
    }

    public <T extends CharSequence> T getMessage(Class<T> type) {
        return type.cast(message);
    }

    public String getFilteredMessage() {
        return getFilteredMessage(String.class);
    }

    public <T extends CharSequence> T getFilteredMessage(Class<T> type) {
        return type.cast(filteredMessage);
    }
}

