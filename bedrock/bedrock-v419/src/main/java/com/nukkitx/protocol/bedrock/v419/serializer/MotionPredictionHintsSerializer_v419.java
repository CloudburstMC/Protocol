package com.nukkitx.protocol.bedrock.v419.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MotionPredictionHintsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MotionPredictionHintsSerializer_v419 implements BedrockPacketSerializer<MotionPredictionHintsPacket> {

    public static final MotionPredictionHintsSerializer_v419 INSTANCE = new MotionPredictionHintsSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MotionPredictionHintsPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getMotion());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MotionPredictionHintsPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setOnGround(buffer.readBoolean());
    }
}
