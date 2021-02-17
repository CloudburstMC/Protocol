package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.CameraShakeAction;
import com.nukkitx.protocol.bedrock.packet.CameraShakePacket;
import com.nukkitx.protocol.bedrock.v419.serializer.CameraShakeSerializer_v419;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraShakeSerializer_v428 extends CameraShakeSerializer_v419 {

    public static final CameraShakeSerializer_v428 INSTANCE = new CameraShakeSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getShakeAction().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CameraShakePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setShakeAction(CameraShakeAction.values()[buffer.readByte()]);
    }
}
