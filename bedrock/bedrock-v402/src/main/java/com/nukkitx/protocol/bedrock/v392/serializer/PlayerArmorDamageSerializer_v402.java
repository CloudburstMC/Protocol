package com.nukkitx.protocol.bedrock.v392.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayerArmorDamagePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerArmorDamageSerializer_v402 implements PacketSerializer<PlayerArmorDamagePacket> {

    public static final PlayerArmorDamageSerializer_v402 INSTANCE = new PlayerArmorDamageSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, PlayerArmorDamagePacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerArmorDamagePacket packet) {

    }
}
