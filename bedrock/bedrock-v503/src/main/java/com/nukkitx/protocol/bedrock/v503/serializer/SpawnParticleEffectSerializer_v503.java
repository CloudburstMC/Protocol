package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import com.nukkitx.protocol.bedrock.v332.serializer.SpawnParticleEffectSerializer_v332;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpawnParticleEffectSerializer_v503 extends SpawnParticleEffectSerializer_v332 {
    public static final SpawnParticleEffectSerializer_v503 INSTANCE = new SpawnParticleEffectSerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptional(buffer, Optional::isPresent, packet.getMolangVariablesJson(), (buf, s) -> helper.writeString(buf, s.get()));
    }

    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SpawnParticleEffectPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setMolangVariablesJson(helper.readOptional(buffer, Optional.empty(), buf -> Optional.of(helper.readString(buf))));
    }
}