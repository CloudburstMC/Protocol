package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventorySlotSerializer_v291 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v291 INSTANCE = new InventorySlotSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        helper.writeItem(buffer, packet.getItem());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));
        packet.setItem(helper.readItem(buffer));
    }
}
