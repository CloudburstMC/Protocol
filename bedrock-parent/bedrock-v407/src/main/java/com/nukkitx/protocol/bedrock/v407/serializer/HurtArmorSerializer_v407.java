package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HurtArmorSerializer_v407 implements BedrockPacketSerializer<HurtArmorPacket> {
    public static final HurtArmorSerializer_v407 INSTANCE = new HurtArmorSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
        VarInts.writeInt(buffer, packet.getCause());
        VarInts.writeInt(buffer, packet.getDamage());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, HurtArmorPacket packet) {
        packet.setCause(VarInts.readInt(buffer));
        packet.setDamage(VarInts.readInt(buffer));
    }
}
