package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaPlayerHurtPacket;
import io.netty.buffer.ByteBuf;

public class GenoaPlayerHurtPacketSerializer implements BedrockPacketSerializer<GenoaPlayerHurtPacket> {
    public static final GenoaPlayerHurtPacketSerializer INSTANCE = new GenoaPlayerHurtPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaPlayerHurtPacket packet) {
        buffer.writeInt(packet.getInt1());
        buffer.writeInt(packet.getInt2());
        buffer.writeFloat(packet.getFloat1());
        buffer.writeFloat(packet.getFloat2());
        buffer.writeFloat(packet.getFloat3());
        buffer.writeFloat(packet.getFloat4());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaPlayerHurtPacket packet) {
        packet.setInt1(buffer.readInt());
        packet.setInt2(buffer.readInt());
        packet.setFloat1(buffer.readFloat());
        packet.setFloat2(buffer.readFloat());
        packet.setFloat3(buffer.readFloat());
        packet.setFloat4(buffer.readFloat());
    }
}
