package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import static com.nukkitx.network.VarInts.readInt;

public class AddPaintingPacket_v291 extends AddPaintingPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeVector3i(buffer, blockPosition);
        VarInts.writeInt(buffer, rotation);
        BedrockUtils.writeString(buffer, title);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        rotation = readInt(buffer);
        title = BedrockUtils.readString(buffer);
    }
}
