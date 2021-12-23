package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CameraPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraSerializer_v291 implements BedrockPacketSerializer<CameraPacket> {
    public static final CameraSerializer_v291 INSTANCE = new CameraSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraPacket packet) {
        VarInts.writeLong(buffer, packet.getCameraUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraPacket packet) {
        packet.setCameraUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
    }
}
