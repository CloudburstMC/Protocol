package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerAuthInputSerializer_v388 implements PacketSerializer<PlayerAuthInputPacket> {

    public static final PlayerAuthInputSerializer_v388 INSTANCE = new PlayerAuthInputSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, PlayerAuthInputPacket packet) {
        Vector3f rotation = packet.getRotation();
        buffer.writeFloatLE(rotation.getX());
        buffer.writeFloatLE(rotation.getY());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        buffer.writeFloatLE(packet.getMotion().getX());
        buffer.writeFloatLE(packet.getMotion().getY());
        buffer.writeFloatLE(rotation.getZ());
        VarInts.writeUnsignedLong(buffer, packet.getInputData());
        VarInts.writeUnsignedInt(buffer, packet.getInputMode());
        VarInts.writeUnsignedInt(buffer, packet.getPlayMode());

        if (packet.getPlayMode() == 4) { // VR MODE
            BedrockUtils.writeVector3f(buffer, packet.getVrGazeDirection());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerAuthInputPacket packet) {
        float x = buffer.readFloatLE();
        float y = buffer.readFloatLE();
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setMotion(Vector2f.from(buffer.readFloatLE(), buffer.readFloatLE()));
        float z = buffer.readFloatLE();
        packet.setRotation(Vector3f.from(x, y, z));
        packet.setInputData(VarInts.readUnsignedLong(buffer));
        packet.setInputMode(VarInts.readUnsignedInt(buffer));
        packet.setPlayMode(VarInts.readUnsignedInt(buffer));

        if (packet.getPlayMode() == 4) {
            packet.setVrGazeDirection(BedrockUtils.readVector3f(buffer));
        }
    }
}
