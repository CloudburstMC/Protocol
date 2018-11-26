package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ContainerOpenPacket_v291 extends ContainerOpenPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
        buffer.writeByte(type);
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        VarInts.writeLong(buffer, uniqueEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readByte();
        type = buffer.readByte();
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        uniqueEntityId = VarInts.readLong(buffer);
    }
}
