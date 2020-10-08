package com.nukkitx.protocol.bedrock.v418.serializer;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ComponentItemData;
import com.nukkitx.protocol.bedrock.packet.ItemComponentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemComponentSerializer_v418 implements BedrockPacketSerializer<ItemComponentPacket> {

    public static final ItemComponentSerializer_v418 INSTANCE = new ItemComponentSerializer_v418();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemComponentPacket packet) {
        helper.writeArray(buffer, packet.getItems(), (buf, packetHelper, item) -> {
            packetHelper.writeString(buf, item.getName());
            packetHelper.writeTag(buf, item.getData());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemComponentPacket packet) {
        helper.readArray(buffer, packet.getItems(), (buf, packetHelper) -> {
            String name = packetHelper.readString(buf);
            NbtMap data = packetHelper.readTag(buf);

            return new ComponentItemData(name, data);
        });
    }
}
