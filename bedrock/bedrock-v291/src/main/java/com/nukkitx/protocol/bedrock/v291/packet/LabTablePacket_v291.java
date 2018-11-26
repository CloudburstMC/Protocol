package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.LabTablePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class LabTablePacket_v291 extends LabTablePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(unknownByte0);
        BedrockUtils.writeVector3i(buffer, blockEntityPosition);
        buffer.writeByte(reactionType);
    }

    @Override
    public void decode(ByteBuf buffer) {
        unknownByte0 = buffer.readByte();
        blockEntityPosition = BedrockUtils.readVector3i(buffer);
        reactionType = buffer.readByte();
    }
}
