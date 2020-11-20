package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobArmorEquipmentSerializer_v291 implements BedrockPacketSerializer<MobArmorEquipmentPacket> {
    public static final MobArmorEquipmentSerializer_v291 INSTANCE = new MobArmorEquipmentSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MobArmorEquipmentPacket packet, BedrockSession session) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeItem(buffer, packet.getHelmet(), session);
        helper.writeItem(buffer, packet.getChestplate(), session);
        helper.writeItem(buffer, packet.getLeggings(), session);
        helper.writeItem(buffer, packet.getBoots(), session);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MobArmorEquipmentPacket packet, BedrockSession session) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setHelmet(helper.readItem(buffer, session));
        packet.setChestplate(helper.readItem(buffer, session));
        packet.setLeggings(helper.readItem(buffer, session));
        packet.setBoots(helper.readItem(buffer, session));
    }
}
