package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
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
        BedrockUtils.writeArray(buffer, packet.getContents(), BedrockUtils::writeItemInstance);
    }

    @Override
    public void deserialize(ByteBuf buffer, InventoryContentPacket packet) {
        packet.setWindowId(VarInts.readUnsignedInt(buffer));
        BedrockUtils.readArray(buffer, packet.getContents(), BedrockUtils::readItemInstance);
    }
}
