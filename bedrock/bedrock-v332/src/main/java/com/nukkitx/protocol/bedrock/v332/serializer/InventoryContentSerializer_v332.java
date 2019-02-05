package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ContainerId;
import com.nukkitx.protocol.bedrock.data.Item;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryContentSerializer_v332 implements PacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v332 INSTANCE = new InventoryContentSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId().id());

        Item[] contents = packet.getContents();
        VarInts.writeUnsignedInt(buffer, contents.length);
        for (Item content : contents) {
            BedrockUtils.writeItemInstance(buffer, content);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, InventoryContentPacket packet) {
        packet.setContainerId(ContainerId.byId(VarInts.readUnsignedInt(buffer)));

        Item[] contents = new Item[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = BedrockUtils.readItemInstance(buffer);
        }
        packet.setContents(contents);
    }
}
