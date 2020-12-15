package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.GuiDataPickItemPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuiDataPickItemSerializer_v291 implements BedrockPacketSerializer<GuiDataPickItemPacket> {
    public static final GuiDataPickItemSerializer_v291 INSTANCE = new GuiDataPickItemSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GuiDataPickItemPacket packet) {
        helper.writeString(buffer, packet.getDescription());
        helper.writeString(buffer, packet.getItemEffects());
        buffer.writeIntLE(packet.getHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GuiDataPickItemPacket packet) {
        packet.setDescription(helper.readString(buffer));
        packet.setItemEffects(helper.readString(buffer));
        packet.setHotbarSlot(buffer.readIntLE());
    }
}
