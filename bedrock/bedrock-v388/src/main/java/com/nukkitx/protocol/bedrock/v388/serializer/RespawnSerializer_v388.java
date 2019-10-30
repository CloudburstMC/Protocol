package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RespawnSerializer_v388 implements PacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v388 INSTANCE = new RespawnSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, RespawnPacket packet) {
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        buffer.writeByte(packet.getSpawnState());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, RespawnPacket packet) {
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setSpawnState(buffer.readUnsignedByte());
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
