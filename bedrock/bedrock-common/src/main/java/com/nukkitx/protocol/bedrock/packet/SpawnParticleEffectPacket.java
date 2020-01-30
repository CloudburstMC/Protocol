package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpawnParticleEffectPacket extends BedrockPacket {
    private int dimensionId;
    private long uniqueEntityId = -1;
    private Vector3f position;
    private String identifier;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SPAWN_PARTICLE_EFFECT;
    }
}
