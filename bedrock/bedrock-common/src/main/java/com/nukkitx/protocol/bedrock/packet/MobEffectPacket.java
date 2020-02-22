package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MobEffectPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Event event;
    private int effectId;
    private int amplifier;
    private boolean particles;
    private int duration;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_EFFECT;
    }

    public enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }
}
