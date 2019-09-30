package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryContentSerializer_v354 implements PacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v354 INSTANCE = new InventoryContentSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());

        ItemData[] contents = packet.getContents();
        VarInts.writeUnsignedInt(buffer, contents.length);
        for (ItemData content : contents) {
            BedrockUtils.writeItemData(buffer, content);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, InventoryContentPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));

        ItemData[] contents = new ItemData[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = BedrockUtils.readItemData(buffer);
        }
        packet.setContents(contents);
    }
}
