package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityMotionSerializer_v291 implements BedrockPacketSerializer<SetEntityMotionPacket> {
    public static final SetEntityMotionSerializer_v291 INSTANCE = new SetEntityMotionSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getMotion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityMotionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setMotion(helper.readVector3f(buffer));
    }
}
