package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class RemoveObjectivePacket_v291 extends RemoveObjectivePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, objectiveId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        objectiveId = BedrockUtils.readString(buffer);
    }
}
