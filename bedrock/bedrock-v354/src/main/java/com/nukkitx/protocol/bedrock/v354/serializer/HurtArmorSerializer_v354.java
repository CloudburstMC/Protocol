package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HurtArmorSerializer_v354 implements PacketSerializer<HurtArmorPacket> {
    public static final HurtArmorSerializer_v354 INSTANCE = new HurtArmorSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, HurtArmorPacket packet) {
        VarInts.writeInt(buffer, packet.getHealth());
    }

    @Override
    public void deserialize(ByteBuf buffer, HurtArmorPacket packet) {
        packet.setHealth(VarInts.readInt(buffer));
    }
}
