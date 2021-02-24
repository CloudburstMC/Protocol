package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaThirdPersonItemParticlePacket;
import io.netty.buffer.ByteBuf;

public class GenoaThirdPersonItemParticlePacketSerializer implements BedrockPacketSerializer<GenoaThirdPersonItemParticlePacket> {
    public static final GenoaThirdPersonItemParticlePacketSerializer INSTANCE = new GenoaThirdPersonItemParticlePacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaThirdPersonItemParticlePacket packet) {
        VarInts.writeInt(buffer, packet.getInt1());
        VarInts.writeInt(buffer, packet.getInt2());
        helper.writeVector3f(buffer, packet.getItemPos());
        helper.writeVector3f(buffer, packet.getViewDirection());
        VarInts.writeUnsignedLong(buffer, packet.getUnsignedVarLong1());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaThirdPersonItemParticlePacket packet) {
        packet.setInt1(VarInts.readInt(buffer));
        packet.setInt2(VarInts.readInt(buffer));
        packet.setItemPos(helper.readVector3f(buffer));
        packet.setViewDirection(helper.readVector3f(buffer));
        packet.setUnsignedVarLong1((VarInts.readUnsignedLong(buffer)));
    }
}
