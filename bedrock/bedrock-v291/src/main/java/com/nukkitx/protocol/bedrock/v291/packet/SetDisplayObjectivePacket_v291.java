package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class SetDisplayObjectivePacket_v291 extends SetDisplayObjectivePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, displaySlot);
        BedrockUtils.writeString(buffer, objectiveId);
        BedrockUtils.writeString(buffer, displayName);
        BedrockUtils.writeString(buffer, criteria);
        VarInts.writeInt(buffer, sortOrder);
    }

    @Override
    public void decode(ByteBuf buffer) {
        displaySlot = BedrockUtils.readString(buffer);
        objectiveId = BedrockUtils.readString(buffer);
        displayName = BedrockUtils.readString(buffer);
        criteria = BedrockUtils.readString(buffer);
        sortOrder = VarInts.readInt(buffer);
    }
}
