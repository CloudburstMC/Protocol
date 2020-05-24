package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.TakeItemEntityPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TakeItemEntitySerializer_v363 implements PacketSerializer<TakeItemEntityPacket> {
    public static final TakeItemEntitySerializer_v363 INSTANCE = new TakeItemEntitySerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, TakeItemEntityPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getItemRuntimeEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, TakeItemEntityPacket packet) {
        packet.setItemRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
