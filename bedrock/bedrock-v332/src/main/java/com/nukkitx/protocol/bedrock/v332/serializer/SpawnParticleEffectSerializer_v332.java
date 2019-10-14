package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpawnParticleEffectSerializer_v332 implements PacketSerializer<SpawnParticleEffectPacket> {
    public static final SpawnParticleEffectSerializer_v332 INSTANCE = new SpawnParticleEffectSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, SpawnParticleEffectPacket packet) {
        buffer.writeByte(packet.getDimensionId());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeString(buffer, packet.getIdentifier());
    }

    @Override
    public void deserialize(ByteBuf buffer, SpawnParticleEffectPacket packet) {
        packet.setDimensionId(buffer.readUnsignedByte());
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setIdentifier(BedrockUtils.readString(buffer));
    }
}
