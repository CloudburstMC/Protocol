package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SpawnExperienceOrbPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpawnExperienceOrbSerializer_v291 implements BedrockPacketSerializer<SpawnExperienceOrbPacket> {
    public static final SpawnExperienceOrbSerializer_v291 INSTANCE = new SpawnExperienceOrbSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SpawnExperienceOrbPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getAmount());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SpawnExperienceOrbPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setAmount(VarInts.readInt(buffer));
    }
}
