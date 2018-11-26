package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class SetEntityDataPacket_v291 extends SetEntityDataPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeMetadata(buffer, metadata);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        BedrockUtils.readMetadata(buffer, metadata);
    }
}
