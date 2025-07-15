package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v729.serializer.CameraAimAssistSerializer_v729;
import org.cloudburstmc.protocol.bedrock.codec.v766.serializer.CameraAimAssistSerializer_v766;
import org.cloudburstmc.protocol.bedrock.packet.CameraAimAssistPacket;

public class CameraAimAssistSerializer_v827 extends CameraAimAssistSerializer_v766 {

    public static final CameraAimAssistSerializer_v827 INSTANCE = new CameraAimAssistSerializer_v827();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isShowDebugRender());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraAimAssistPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setShowDebugRender(buffer.readBoolean());
    }
}
