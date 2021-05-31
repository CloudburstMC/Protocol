package org.cloudburstmc.protocol.bedrock.codec.v354.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.OnScreenTextureAnimationPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnScreenTextureAnimationSerializer_v354 implements BedrockPacketSerializer<OnScreenTextureAnimationPacket> {
    public static final OnScreenTextureAnimationSerializer_v354 INSTANCE = new OnScreenTextureAnimationSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, OnScreenTextureAnimationPacket packet) {
        buffer.writeIntLE((int) packet.getEffectId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, OnScreenTextureAnimationPacket packet) {
        packet.setEffectId(buffer.readUnsignedIntLE());
    }
}
