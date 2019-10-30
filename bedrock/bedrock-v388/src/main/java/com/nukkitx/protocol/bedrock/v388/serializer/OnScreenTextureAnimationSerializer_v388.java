package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.OnScreenTextureAnimationPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OnScreenTextureAnimationSerializer_v388 implements PacketSerializer<OnScreenTextureAnimationPacket> {
    public static final OnScreenTextureAnimationSerializer_v388 INSTANCE = new OnScreenTextureAnimationSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, OnScreenTextureAnimationPacket packet) {
        buffer.writeIntLE((int) packet.getEffectId());
    }

    @Override
    public void deserialize(ByteBuf buffer, OnScreenTextureAnimationPacket packet) {
        packet.setEffectId(buffer.readUnsignedIntLE());
    }
}
