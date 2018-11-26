package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class BlockEventPacket_v291 extends BlockEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        VarInts.writeInt(buffer, eventType);
        VarInts.writeInt(buffer, eventData);
    }

    @Override
    public void decode(ByteBuf buffer) {
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        eventType = VarInts.readInt(buffer);
        eventData = VarInts.readInt(buffer);
    }
}
