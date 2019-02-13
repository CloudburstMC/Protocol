package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnimatePacket extends BedrockPacket {
    private float rowingTime;
    private Action action;
    private long runtimeEntityId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Action {
        NO_ACTION,
        SWING_ARM,
        WAKE_UP,
        CRITICAL_HIT,
        MAGIC_CRITICAL_HIT,
        ROW_RIGHT,
        ROW_LEFT,
    }
}
