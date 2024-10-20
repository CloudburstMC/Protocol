package org.cloudburstmc.protocol.bedrock.codec.v748.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InventorySlotSerializer_v748 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v748 INSTANCE = new InventorySlotSerializer_v748();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        helper.writeFullContainerName(buffer, packet.getContainerNameData());
        helper.writeNetItem(buffer, packet.getStorageItem());
        helper.writeNetItem(buffer, packet.getItem());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));
        packet.setContainerNameData(helper.readFullContainerName(buffer));
        packet.setStorageItem(helper.readNetItem(buffer));
        packet.setItem(helper.readNetItem(buffer));
    }
}