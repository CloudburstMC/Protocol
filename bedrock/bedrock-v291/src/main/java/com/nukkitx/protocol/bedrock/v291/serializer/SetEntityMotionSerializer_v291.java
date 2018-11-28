package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetEntityMotionSerializer_v291 implements PacketSerializer<SetEntityMotionPacket> {
    public static final SetEntityMotionSerializer_v291 INSTANCE = new SetEntityMotionSerializer_v291();


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
