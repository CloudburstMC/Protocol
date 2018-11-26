package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetEntityMotionPacket_v291 extends SetEntityMotionPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeVector3f(buffer, motion);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        motion = BedrockUtils.readVector3f(buffer);
    }
}
