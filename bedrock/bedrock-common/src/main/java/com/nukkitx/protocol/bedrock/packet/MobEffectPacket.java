package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class MobEffectPacket extends BedrockPacket {
    protected long runtimeEntityId;
    protected Event event;
    protected int effectId;
    protected int amplifier;
    protected boolean particles;
    protected int duration;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }
}
