package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MobArmorEquipmentSerializer_v332 implements PacketSerializer<MobArmorEquipmentPacket> {
    public static final MobArmorEquipmentSerializer_v332 INSTANCE = new MobArmorEquipmentSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, MobArmorEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeItemInstance(buffer, packet.getHelmet());
        BedrockUtils.writeItemInstance(buffer, packet.getChestplate());
        BedrockUtils.writeItemInstance(buffer, packet.getLeggings());
        BedrockUtils.writeItemInstance(buffer, packet.getBoots());
    }

    @Override
    public void deserialize(ByteBuf buffer, MobArmorEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setHelmet(BedrockUtils.readItemInstance(buffer));
        packet.setChestplate(BedrockUtils.readItemInstance(buffer));
        packet.setLeggings(BedrockUtils.readItemInstance(buffer));
        packet.setBoots(BedrockUtils.readItemInstance(buffer));
    }
}
