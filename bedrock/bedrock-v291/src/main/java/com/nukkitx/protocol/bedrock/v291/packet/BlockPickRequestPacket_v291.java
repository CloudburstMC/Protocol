package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.BlockPickRequestPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class BlockPickRequestPacket_v291 extends BlockPickRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeVector3i(buffer, blockPosition);
        buffer.writeBoolean(addUserData);
        buffer.writeByte(hotbarSlot);
    }

    @Override
    public void decode(ByteBuf buffer) {
        blockPosition = BedrockUtils.readVector3i(buffer);
        addUserData = buffer.readBoolean();
        hotbarSlot = buffer.readByte();
    }
}
