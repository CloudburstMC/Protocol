package com.nukkitx.protocol.bedrock.v418.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPlusPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityMotionPlusSerializer_v418 implements BedrockPacketSerializer<SetEntityMotionPlusPacket> {

    public static final SetEntityMotionPlusSerializer_v418 INSTANCE = new SetEntityMotionPlusSerializer_v418();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPlusPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getMotion());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPlusPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setOnGround(buffer.readBoolean());
    }
}
