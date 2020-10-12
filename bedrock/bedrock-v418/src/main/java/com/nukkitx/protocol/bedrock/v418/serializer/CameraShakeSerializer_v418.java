package com.nukkitx.protocol.bedrock.v418.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CameraShakePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraShakeSerializer_v418 implements BedrockPacketSerializer<CameraShakePacket> {

    public static final CameraShakeSerializer_v418 INSTANCE = new CameraShakeSerializer_v418();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        buffer.writeFloatLE(packet.getIntensity());
        buffer.writeFloatLE(packet.getDuration());
        buffer.writeByte(packet.getShakeType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        packet.setIntensity(buffer.readFloatLE());
        packet.setDuration(buffer.readFloatLE());
        CameraShakePacket.CameraShakeType cameraShakeType = CameraShakePacket.CameraShakeType.values()[buffer.readUnsignedByte()];
        packet.setShakeType(cameraShakeType);
    }
}
