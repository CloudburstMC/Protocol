package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetTitlePacket extends BedrockPacket {
    private Type type;
    private String text;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;
    private String xuid;
    private String platformOnlineId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
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
