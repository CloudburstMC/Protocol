package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class SetTitlePacket extends BedrockPacket {
    protected Type type;
    protected String text;
    protected int fadeInTime;
    protected int stayTime;
    protected int fadeOutTime;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        CLEAR_TITLE,
        RESET_TITLE,
        SET_TITLE,
        SET_SUBTITLE,
        SET_ACTIONBAR_MESSAGE,
        SET_ANIMATION_TIMES
    }
}
