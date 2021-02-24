package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaUpdateBlockPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenoaUpdateBlockPacketSerializer implements BedrockPacketSerializer<GenoaUpdateBlockPacket> {
    public static final GenoaUpdateBlockPacketSerializer INSTANCE = new GenoaUpdateBlockPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaUpdateBlockPacket packet) {
        helper.writeBlockPosition(buffer,packet.getBlockPos());
        VarInts.writeUnsignedInt(buffer,packet.getUnsignedVarInt2());
        VarInts.writeUnsignedInt(buffer,packet.getUnsignedVarInt3());
        if (packet.isCheckForFloat()) {
            buffer.writeFloat(packet.getFloat1());
            buffer.writeBoolean(true);
        } else {
            VarInts.writeUnsignedInt(buffer,packet.getUnsignedVarInt4());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaUpdateBlockPacket packet) {
        packet.setBlockPos(helper.readBlockPosition(buffer));
        packet.setUnsignedVarInt2(VarInts.readUnsignedInt(buffer));
        packet.setUnsignedVarInt3(VarInts.readUnsignedInt(buffer));
        float ftemp = buffer.readFloat();
        boolean temp = buffer.readBoolean();
        if (!temp) {
            packet.setUnsignedVarInt4(VarInts.readUnsignedInt(buffer));
        } else {
            packet.setFloat1(ftemp);
            packet.setCheckForFloat(true);
        }
    }
}
