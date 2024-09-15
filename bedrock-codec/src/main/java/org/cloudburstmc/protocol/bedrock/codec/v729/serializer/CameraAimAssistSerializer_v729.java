package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraAimAssistSerializer_v729 implements BedrockPacketSerializer<CameraAimAssistPacket> {
    public static final CameraAimAssistSerializer_v729 INSTANCE = new CameraAimAssistSerializer_v729();

    protected static final CameraAimAssistPacket.Action[] ACTIONS = CameraAimAssistPacket.Action.values();
    protected static final CameraAimAssistPacket.TargetMode[] TARGET_MODES = CameraAimAssistPacket.TargetMode.values();

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
        packet.setTargetMode(TARGET_MODES[buffer.readUnsignedByte()]);
        packet.setAction(ACTIONS[buffer.readUnsignedByte()]);
    }
}
