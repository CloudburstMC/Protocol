package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class MobArmorEquipmentPacket_v291 extends MobArmorEquipmentPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeItemInstance(buffer, helmet);
        BedrockUtils.writeItemInstance(buffer, chestplate);
        BedrockUtils.writeItemInstance(buffer, leggings);
        BedrockUtils.writeItemInstance(buffer, boots);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        helmet = BedrockUtils.readItemInstance(buffer);
        chestplate = BedrockUtils.readItemInstance(buffer);
        leggings = BedrockUtils.readItemInstance(buffer);
        boots = BedrockUtils.readItemInstance(buffer);
    }
}
