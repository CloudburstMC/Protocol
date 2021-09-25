package com.nukkitx.protocol.bedrock.v465.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import com.nukkitx.protocol.bedrock.v407.serializer.HurtArmorSerializer_v407;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HurtArmorSerializer_v465 extends HurtArmorSerializer_v407 {

    public static final HurtArmorSerializer_v465 INSTANCE = new HurtArmorSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedLong(buffer, packet.getArmorSlots());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setArmorSlots(VarInts.readUnsignedLong(buffer));
    }
}
