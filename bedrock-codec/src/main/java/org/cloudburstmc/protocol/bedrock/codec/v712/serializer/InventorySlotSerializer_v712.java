package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class InventorySlotSerializer_v712 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v712 INSTANCE = new InventorySlotSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        VarInts.writeUnsignedInt(buffer, packet.getContainerNameData() == null || packet.getContainerNameData().getDynamicId() == null ? 0 : packet.getContainerNameData().getDynamicId());
        helper.writeNetItem(buffer, packet.getItem());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));

        FullContainerName containerName = new FullContainerName(ContainerSlotType.UNKNOWN,
                VarInts.readUnsignedInt(buffer));
        packet.setContainerNameData(containerName);

        packet.setItem(helper.readNetItem(buffer));
    }
}