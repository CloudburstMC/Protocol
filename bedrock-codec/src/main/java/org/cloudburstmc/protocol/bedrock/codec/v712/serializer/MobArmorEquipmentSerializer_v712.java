package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.MobArmorEquipmentSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.MobArmorEquipmentPacket;

public class MobArmorEquipmentSerializer_v712 extends MobArmorEquipmentSerializer_v291 {
    public static final MobArmorEquipmentSerializer_v712 INSTANCE = new MobArmorEquipmentSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobArmorEquipmentPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeItem(buffer, packet.getBody());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobArmorEquipmentPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setBody(helper.readItem(buffer));
    }
}