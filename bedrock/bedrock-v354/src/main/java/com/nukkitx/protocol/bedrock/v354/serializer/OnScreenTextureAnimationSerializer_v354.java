package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.OnScreenTextureAnimationPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnScreenTextureAnimationSerializer_v354 implements BedrockPacketSerializer<OnScreenTextureAnimationPacket> {
    public static final OnScreenTextureAnimationSerializer_v354 INSTANCE = new OnScreenTextureAnimationSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, OnScreenTextureAnimationPacket packet) {
        buffer.writeIntLE((int) packet.getEffectId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, OnScreenTextureAnimationPacket packet) {
        packet.setEffectId(buffer.readUnsignedIntLE());
    }
}
