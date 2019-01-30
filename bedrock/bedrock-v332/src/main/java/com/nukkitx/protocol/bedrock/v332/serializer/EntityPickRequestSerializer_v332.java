package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityPickRequestSerializer_v332 implements PacketSerializer<EntityPickRequestPacket> {
    public static final EntityPickRequestSerializer_v332 INSTANCE = new EntityPickRequestSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, EntityPickRequestPacket packet) {
        buffer.writeLongLE(packet.getRuntimeEntityId());
        buffer.writeByte(packet.getHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, EntityPickRequestPacket packet) {
        packet.setRuntimeEntityId(buffer.readLongLE());
        packet.setHotbarSlot(buffer.readUnsignedByte());
    }
}
