package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.GuiDataPickItemPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class GuiDataPickItemPacket_v291 extends GuiDataPickItemPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, description);
        BedrockUtils.writeString(buffer, itemEffects);
        buffer.writeIntLE(hotbarSlot);
    }

    @Override
    public void decode(ByteBuf buffer) {
        description = BedrockUtils.readString(buffer);
        itemEffects = BedrockUtils.readString(buffer);
        hotbarSlot = buffer.readIntLE();
    }
}
