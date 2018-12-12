package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.GuiDataPickItemPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuiDataPickItemSerializer_v313 implements PacketSerializer<GuiDataPickItemPacket> {
    public static final GuiDataPickItemSerializer_v313 INSTANCE = new GuiDataPickItemSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, GuiDataPickItemPacket packet) {
        BedrockUtils.writeString(buffer, packet.getDescription());
        BedrockUtils.writeString(buffer, packet.getItemEffects());
        buffer.writeIntLE(packet.getHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, GuiDataPickItemPacket packet) {
        packet.setDescription(BedrockUtils.readString(buffer));
        packet.setItemEffects(BedrockUtils.readString(buffer));
        packet.setHotbarSlot(buffer.readIntLE());
    }
}
