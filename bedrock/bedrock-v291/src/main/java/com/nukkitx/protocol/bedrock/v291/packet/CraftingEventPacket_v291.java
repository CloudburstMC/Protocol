package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class CraftingEventPacket_v291 extends CraftingEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
        VarInts.writeInt(buffer, type);
        BedrockUtils.writeUuid(buffer, uuid);

        BedrockUtils.writeArray(buffer, inputs, BedrockUtils::writeItemInstance);

        BedrockUtils.writeArray(buffer, outputs, BedrockUtils::writeItemInstance);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readByte();
        type = VarInts.readInt(buffer);
        uuid = BedrockUtils.readUuid(buffer);

        BedrockUtils.readArray(buffer, inputs, BedrockUtils::readItemInstance);

        BedrockUtils.readArray(buffer, outputs, BedrockUtils::readItemInstance);
    }
}
