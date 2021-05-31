package org.cloudburstmc.protocol.bedrock.codec.v428.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.CameraShakeSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.CameraShakeAction;
import org.cloudburstmc.protocol.bedrock.packet.CameraShakePacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraShakeSerializer_v428 extends CameraShakeSerializer_v419 {

    public static final CameraShakeSerializer_v428 INSTANCE = new CameraShakeSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraShakePacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getShakeAction().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraShakePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setShakeAction(CameraShakeAction.values()[buffer.readByte()]);
    }
}
