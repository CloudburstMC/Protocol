package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetSpawnPositionPacket_v291 extends SetSpawnPositionPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, spawnType.ordinal());
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        buffer.writeBoolean(spawnForced);
    }

    @Override
    public void decode(ByteBuf buffer) {
        spawnType = Type.values()[VarInts.readInt(buffer)];
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        spawnForced = buffer.readBoolean();
    }
}
