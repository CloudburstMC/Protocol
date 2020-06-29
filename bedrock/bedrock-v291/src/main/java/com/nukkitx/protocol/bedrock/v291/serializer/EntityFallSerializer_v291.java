package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EntityFallPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFallSerializer_v291 implements BedrockPacketSerializer<EntityFallPacket> {
    public static final EntityFallSerializer_v291 INSTANCE = new EntityFallSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityFallPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeFloatLE(packet.getFallDistance());
        buffer.writeBoolean(packet.isInVoid());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityFallPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setFallDistance(buffer.readFloatLE());
        packet.setInVoid(buffer.readBoolean());
    }
}
