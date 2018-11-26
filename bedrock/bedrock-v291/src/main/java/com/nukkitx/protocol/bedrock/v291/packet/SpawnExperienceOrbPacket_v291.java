package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SpawnExperienceOrbPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SpawnExperienceOrbPacket_v291 extends SpawnExperienceOrbPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector3f(buffer, position);
        VarInts.writeInt(buffer, amount);
    }

    @Override
    public void decode(ByteBuf buffer) {
        position = BedrockUtils.readVector3f(buffer);
        amount = VarInts.readInt(buffer);
    }
}
