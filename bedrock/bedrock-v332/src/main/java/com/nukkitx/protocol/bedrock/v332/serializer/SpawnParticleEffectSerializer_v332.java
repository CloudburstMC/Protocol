package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpawnParticleEffectSerializer_v332 implements BedrockPacketSerializer<SpawnParticleEffectPacket> {
    public static final SpawnParticleEffectSerializer_v332 INSTANCE = new SpawnParticleEffectSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
        buffer.writeByte(packet.getDimensionId());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeString(buffer, packet.getIdentifier());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
        packet.setDimensionId(buffer.readUnsignedByte());
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setIdentifier(helper.readString(buffer));
    }
}
