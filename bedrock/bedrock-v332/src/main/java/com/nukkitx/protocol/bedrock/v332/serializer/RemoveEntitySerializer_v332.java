package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoveEntitySerializer_v332 implements PacketSerializer<RemoveEntityPacket> {
    public static final RemoveEntitySerializer_v332 INSTANCE = new RemoveEntitySerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, RemoveEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, RemoveEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
    }
}
