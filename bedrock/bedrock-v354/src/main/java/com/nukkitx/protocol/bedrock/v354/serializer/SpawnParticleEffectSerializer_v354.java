package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpawnParticleEffectSerializer_v354 implements PacketSerializer<SpawnParticleEffectPacket> {
    public static final SpawnParticleEffectSerializer_v354 INSTANCE = new SpawnParticleEffectSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, SpawnParticleEffectPacket packet) {
        buffer.writeShortLE(packet.getDimensionId());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeString(buffer, packet.getIdentifier());
    }

    @Override
    public void deserialize(ByteBuf buffer, SpawnParticleEffectPacket packet) {
        packet.setDimensionId(buffer.readUnsignedShortLE());
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setIdentifier(BedrockUtils.readString(buffer));
    }
}
