package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import com.nukkitx.nbt.NbtMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ComponentItemData;
import org.cloudburstmc.protocol.bedrock.packet.ItemComponentPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemComponentSerializer_v419 implements BedrockPacketSerializer<ItemComponentPacket> {

    public static final ItemComponentSerializer_v419 INSTANCE = new ItemComponentSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemComponentPacket packet) {
        helper.writeArray(buffer, packet.getItems(), (buf, packetHelper, item) -> {
            packetHelper.writeString(buf, item.getName());
            packetHelper.writeTag(buf, item.getData());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemComponentPacket packet) {
        helper.readArray(buffer, packet.getItems(), (buf, packetHelper) -> {
            String name = packetHelper.readString(buf);
            NbtMap data = packetHelper.readTag(buf, NbtMap.class);

            return new ComponentItemData(name, data);
        });
    }
}
