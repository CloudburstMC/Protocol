package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EntityFallPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityFallSerializer_v332 implements PacketSerializer<EntityFallPacket> {
    public static final EntityFallSerializer_v332 INSTANCE = new EntityFallSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, EntityFallPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeFloatLE(packet.getFallDistance());
        buffer.writeBoolean(packet.isInVoid());
    }

    @Override
    public void deserialize(ByteBuf buffer, EntityFallPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setFallDistance(buffer.readFloatLE());
        packet.setInVoid(buffer.readBoolean());
    }
}
