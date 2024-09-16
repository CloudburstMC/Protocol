package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.InventoryContentSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class InventoryContentSerializer_v712 extends InventoryContentSerializer_v407 {
    public static final InventoryContentSerializer_v712 INSTANCE = new InventoryContentSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryContentPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedInt(buffer, packet.getContainerNameData() == null || packet.getContainerNameData().getDynamicId() == null ? 0 : packet.getContainerNameData().getDynamicId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryContentPacket packet) {
        super.deserialize(buffer, helper, packet);

        FullContainerName containerName = new FullContainerName(ContainerSlotType.UNKNOWN,
                VarInts.readUnsignedInt(buffer));
        packet.setContainerNameData(containerName);
    }
}