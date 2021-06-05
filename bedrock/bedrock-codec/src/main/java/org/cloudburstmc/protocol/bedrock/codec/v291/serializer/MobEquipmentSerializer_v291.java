package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEquipmentSerializer_v291 implements BedrockPacketSerializer<MobEquipmentPacket> {
    public static final MobEquipmentSerializer_v291 INSTANCE = new MobEquipmentSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeItem(buffer, packet.getItem());
        buffer.writeByte(packet.getInventorySlot());
        buffer.writeByte(packet.getHotbarSlot());
        buffer.writeByte(packet.getContainerId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setItem(helper.readItem(buffer));
        packet.setInventorySlot(buffer.readUnsignedByte());
        packet.setHotbarSlot(buffer.readUnsignedByte());
        packet.setContainerId(buffer.readByte());
    }
}
