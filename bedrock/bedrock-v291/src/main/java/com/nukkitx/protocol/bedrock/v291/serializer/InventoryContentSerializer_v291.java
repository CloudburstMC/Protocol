package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryContentSerializer_v291 implements BedrockPacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v291 INSTANCE = new InventoryContentSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());

        ItemData[] contents = packet.getContents();
        VarInts.writeUnsignedInt(buffer, contents.length);
        for (ItemData content : contents) {
            helper.writeItem(buffer, content);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));

        ItemData[] contents = new ItemData[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = helper.readItem(buffer);
        }
        packet.setContents(contents);
    }
}
