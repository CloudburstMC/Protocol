package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPacket;

public class CameraAimAssistSerializer_v729 implements BedrockPacketSerializer<CameraAimAssistPacket> {
    public static final CameraAimAssistSerializer_v729 INSTANCE = new CameraAimAssistSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPacket packet) {
        helper.writeVector2f(buffer, packet.getViewAngle());
        buffer.writeFloatLE(packet.getDistance());
        buffer.writeByte(packet.getTargetMode().ordinal());
        buffer.writeByte(packet.getAction().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPacket packet) {
        packet.setViewAngle(helper.readVector2f(buffer));
        packet.setDistance(buffer.readFloatLE());
        packet.setTargetMode(CameraAimAssistPacket.TargetMode.values()[buffer.readUnsignedByte()]);
        packet.setAction(CameraAimAssistPacket.Action.values()[buffer.readUnsignedByte()]);
    }
}