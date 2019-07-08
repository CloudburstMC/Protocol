package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HurtArmorSerializer_v361 implements PacketSerializer<HurtArmorPacket> {
    public static final HurtArmorSerializer_v361 INSTANCE = new HurtArmorSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, HurtArmorPacket packet) {
        VarInts.writeInt(buffer, packet.getHealth());
    }

    @Override
    public void deserialize(ByteBuf buffer, HurtArmorPacket packet) {
        packet.setHealth(VarInts.readInt(buffer));
    }
}
