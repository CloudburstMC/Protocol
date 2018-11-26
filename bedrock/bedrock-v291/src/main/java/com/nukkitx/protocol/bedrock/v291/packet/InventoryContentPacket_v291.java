package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class InventoryContentPacket_v291 extends InventoryContentPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, windowId);
        BedrockUtils.writeArray(buffer, contents, BedrockUtils::writeItemInstance);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = VarInts.readUnsignedInt(buffer);
        BedrockUtils.readArray(buffer, contents, BedrockUtils::readItemInstance);
    }
}
