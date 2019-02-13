package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ContainerId;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MobEquipmentSerializer_v291 implements PacketSerializer<MobEquipmentPacket> {
    public static final MobEquipmentSerializer_v291 INSTANCE = new MobEquipmentSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, MobEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeItemData(buffer, packet.getItem());
        buffer.writeByte(packet.getInventorySlot());
        buffer.writeByte(packet.getHotbarSlot());
        buffer.writeByte(packet.getContainerId().id());
    }

    @Override
    public void deserialize(ByteBuf buffer, MobEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setItem(BedrockUtils.readItemData(buffer));
        packet.setInventorySlot(buffer.readUnsignedByte());
        packet.setHotbarSlot(buffer.readUnsignedByte());
        packet.setContainerId(ContainerId.byId(buffer.readByte()));
    }
}
