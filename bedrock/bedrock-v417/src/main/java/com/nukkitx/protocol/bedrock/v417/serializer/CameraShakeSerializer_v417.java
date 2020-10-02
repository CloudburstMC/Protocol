package com.nukkitx.protocol.bedrock.v417.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CameraShakePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraShakeSerializer_v417 implements BedrockPacketSerializer<CameraShakePacket> {

    public static final CameraShakeSerializer_v417 INSTANCE = new CameraShakeSerializer_v417();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        buffer.writeFloatLE(packet.getIntensity());
        buffer.writeFloatLE(packet.getDuration());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        packet.setIntensity(buffer.readFloatLE());
        packet.setDuration(buffer.readFloatLE());
    }
}
