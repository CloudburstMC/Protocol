package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ItemFrameDropItemPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ItemFrameDropItemPacket_v291 extends ItemFrameDropItemPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector3i(buffer, blockPosition);
    }

    @Override
    public void decode(ByteBuf buffer) {
        blockPosition = BedrockUtils.readVector3i(buffer);
    }
}
