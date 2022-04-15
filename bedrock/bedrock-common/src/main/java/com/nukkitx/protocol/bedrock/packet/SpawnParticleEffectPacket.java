package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SpawnParticleEffectPacket extends BedrockPacket {
    private int dimensionId;
    private long uniqueEntityId = -1;
    private Vector3f position;
    private String identifier;
    /**
     * @since v503
     */
    private Optional<String> molangVariablesJson;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SPAWN_PARTICLE_EFFECT;
    }
}
