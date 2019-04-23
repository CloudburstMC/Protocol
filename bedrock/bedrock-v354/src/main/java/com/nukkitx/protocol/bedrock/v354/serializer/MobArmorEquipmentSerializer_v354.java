package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MobArmorEquipmentSerializer_v354 implements PacketSerializer<MobArmorEquipmentPacket> {
    public static final MobArmorEquipmentSerializer_v354 INSTANCE = new MobArmorEquipmentSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, MobArmorEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeItemData(buffer, packet.getHelmet());
        BedrockUtils.writeItemData(buffer, packet.getChestplate());
        BedrockUtils.writeItemData(buffer, packet.getLeggings());
        BedrockUtils.writeItemData(buffer, packet.getBoots());
    }

    @Override
    public void deserialize(ByteBuf buffer, MobArmorEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setHelmet(BedrockUtils.readItemData(buffer));
        packet.setChestplate(BedrockUtils.readItemData(buffer));
        packet.setLeggings(BedrockUtils.readItemData(buffer));
        packet.setBoots(BedrockUtils.readItemData(buffer));
    }
}
