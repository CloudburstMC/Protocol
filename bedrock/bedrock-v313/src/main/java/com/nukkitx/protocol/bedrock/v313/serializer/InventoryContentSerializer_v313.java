package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.Item;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryContentSerializer_v313 implements PacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v313 INSTANCE = new InventoryContentSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getWindowId());

        Item[] contents = new Item[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = BedrockUtils.readItemInstance(buffer);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, InventoryContentPacket packet) {
        packet.setWindowId(VarInts.readUnsignedInt(buffer));

        Item[] contents = packet.getContents();
        VarInts.writeUnsignedInt(buffer, contents.length);
        for (Item content : contents) {
            BedrockUtils.writeItemInstance(buffer, content);
        }
    }
}
