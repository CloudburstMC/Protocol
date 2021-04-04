package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaItemParticlePacket;
import io.netty.buffer.ByteBuf;

public class GenoaItemParticlePacketSerializer implements BedrockPacketSerializer<GenoaItemParticlePacket> {
    public static final GenoaItemParticlePacketSerializer INSTANCE = new GenoaItemParticlePacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaItemParticlePacket packet) {
        buffer.writeIntLE(packet.getParticleId());
        buffer.writeIntLE(packet.getDimensionId());
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeUnsignedLong(buffer, packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaItemParticlePacket packet) {
        packet.setParticleId(buffer.readIntLE());
        packet.setDimensionId(buffer.readIntLE());
        packet.setPosition(helper.readVector3f(buffer));
        packet.setUniqueEntityId((VarInts.readUnsignedLong(buffer)));

    }
}
