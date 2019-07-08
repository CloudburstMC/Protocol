package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RespawnSerializer_v361 implements PacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v361 INSTANCE = new RespawnSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, RespawnPacket packet) {
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, RespawnPacket packet) {
        packet.setPosition(BedrockUtils.readVector3f(buffer));
    }
}
