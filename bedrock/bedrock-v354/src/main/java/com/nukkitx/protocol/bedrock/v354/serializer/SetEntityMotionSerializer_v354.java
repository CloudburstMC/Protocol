package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetEntityMotionSerializer_v354 implements PacketSerializer<SetEntityMotionPacket> {
    public static final SetEntityMotionSerializer_v354 INSTANCE = new SetEntityMotionSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, SetEntityMotionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeVector3f(buffer, packet.getMotion());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetEntityMotionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setMotion(BedrockUtils.readVector3f(buffer));
    }
}
