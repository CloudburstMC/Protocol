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
        VarInts.writeInt(buffer, packet.getInt1());
        VarInts.writeInt(buffer, packet.getInt2());
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeUnsignedLong(buffer, packet.getUnsignedVarLong1());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaItemParticlePacket packet) {
        packet.setInt1(VarInts.readInt(buffer));
        packet.setInt2(VarInts.readInt(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setUnsignedVarLong1((VarInts.readUnsignedLong(buffer)));
    }
}
