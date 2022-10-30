package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.GuiDataPickItemPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuiDataPickItemSerializer_v291 implements BedrockPacketSerializer<GuiDataPickItemPacket> {
    public static final GuiDataPickItemSerializer_v291 INSTANCE = new GuiDataPickItemSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, GuiDataPickItemPacket packet) {
        helper.writeString(buffer, packet.getDescription());
        helper.writeString(buffer, packet.getItemEffects());
        buffer.writeIntLE(packet.getHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, GuiDataPickItemPacket packet) {
        packet.setDescription(helper.readString(buffer));
        packet.setItemEffects(helper.readString(buffer));
        packet.setHotbarSlot(buffer.readIntLE());
    }
}
