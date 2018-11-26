package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ChangeDimensionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ChangeDimensionPacket_v291 extends ChangeDimensionPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, dimension);
        BedrockUtils.writeVector3f(buffer, position);
        buffer.writeBoolean(respawn);
    }

    @Override
    public void decode(ByteBuf buffer) {
        dimension = VarInts.readInt(buffer);
        position = BedrockUtils.readVector3f(buffer);
        respawn = buffer.readBoolean();
    }
}
