package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetTitlePacket implements BedrockPacket {
    private Type type;
    private String text;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;
    /**
     * @since v448
     */
    private String xuid;
    /**
     * @since v448
     */
    private String platformOnlineId;
    /**
     * @since v712
     */
    private String filteredTitleText = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_TITLE;
    }

    public enum Type {
        CLEAR,
        RESET,
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        TITLE_JSON,
        SUBTITLE_JSON,
        ACTIONBAR_JSON
    }
}
