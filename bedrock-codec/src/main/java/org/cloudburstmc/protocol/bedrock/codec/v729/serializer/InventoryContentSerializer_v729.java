package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.InventoryContentSerializer_v407;
import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class InventoryContentSerializer_v729 extends InventoryContentSerializer_v407 {
    public static final InventoryContentSerializer_v729 INSTANCE = new InventoryContentSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryContentPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeFullContainerName(buffer, packet.getFullContainerName());
        VarInts.writeUnsignedInt(buffer, packet.getDynamicContainerSize());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventoryContentPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFullContainerName(helper.readFullContainerName(buffer));
        packet.setDynamicContainerSize(VarInts.readUnsignedInt(buffer));
    }
}