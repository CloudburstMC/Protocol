package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaNetworkTransformPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenoaNetworkTransformSerializer implements BedrockPacketSerializer<GenoaNetworkTransformPacket> {
    public static final GenoaNetworkTransformSerializer INSTANCE = new GenoaNetworkTransformSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkTransformPacket packet) {
        buffer.writeByte(packet.getByte1());
        buffer.writeFloat(packet.getF1());
        buffer.writeFloat(packet.getF2());
        buffer.writeFloat(packet.getF3());
        buffer.writeFloat(packet.getF4());
        buffer.writeFloat(packet.getF5());
        buffer.writeFloat(packet.getF6());
        buffer.writeFloat(packet.getF7());
        buffer.writeFloat(packet.getF8());
        buffer.writeFloat(packet.getF9());
        buffer.writeFloat(packet.getF10());
        buffer.writeFloat(packet.getF11());
        buffer.writeFloat(packet.getF12());
        buffer.writeFloat(packet.getF13());
        buffer.writeFloat(packet.getF14());
        buffer.writeFloat(packet.getF15());
        buffer.writeFloat(packet.getF16());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkTransformPacket packet) {
        packet.setByte1(buffer.readByte());
        packet.setF1(buffer.readFloat());
        packet.setF2(buffer.readFloat());
        packet.setF3(buffer.readFloat());
        packet.setF4(buffer.readFloat());
        packet.setF5(buffer.readFloat());
        packet.setF6(buffer.readFloat());
        packet.setF7(buffer.readFloat());
        packet.setF8(buffer.readFloat());
        packet.setF9(buffer.readFloat());
        packet.setF10(buffer.readFloat());
        packet.setF11(buffer.readFloat());
        packet.setF12(buffer.readFloat());
        packet.setF13(buffer.readFloat());
        packet.setF14(buffer.readFloat());
        packet.setF15(buffer.readFloat());
        packet.setF16(buffer.readFloat());

    }
}


