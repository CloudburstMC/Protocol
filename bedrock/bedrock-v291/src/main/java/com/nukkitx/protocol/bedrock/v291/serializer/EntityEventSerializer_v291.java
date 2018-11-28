package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityEventSerializer_v291 implements PacketSerializer<EntityEventPacket> {
    public static final EntityEventSerializer_v291 INSTANCE = new EntityEventSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, EntityEventPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getEvent());
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, EntityEventPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEvent(buffer.readUnsignedByte());
        packet.setData(VarInts.readInt(buffer));
    }
}
