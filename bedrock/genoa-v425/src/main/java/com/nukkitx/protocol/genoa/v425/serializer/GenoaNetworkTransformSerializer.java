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
        buffer.writeFloatLE(packet.getF1());
        buffer.writeFloatLE(packet.getF2());
        buffer.writeFloatLE(packet.getF3());
        buffer.writeFloatLE(packet.getF4());
        buffer.writeFloatLE(packet.getF5());
        buffer.writeFloatLE(packet.getF6());
        buffer.writeFloatLE(packet.getF7());
        buffer.writeFloatLE(packet.getF8());
        buffer.writeFloatLE(packet.getF9());
        buffer.writeFloatLE(packet.getF10());
        buffer.writeFloatLE(packet.getF11());
        buffer.writeFloatLE(packet.getF12());
        buffer.writeFloatLE(packet.getF13());
        buffer.writeFloatLE(packet.getF14());
        buffer.writeFloatLE(packet.getF15());
        buffer.writeFloatLE(packet.getF16());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkTransformPacket packet) {
        packet.setByte1(buffer.readByte());
        packet.setF1(buffer.readFloatLE());
        packet.setF2(buffer.readFloatLE());
        packet.setF3(buffer.readFloatLE());
        packet.setF4(buffer.readFloatLE());
        packet.setF5(buffer.readFloatLE());
        packet.setF6(buffer.readFloatLE());
        packet.setF7(buffer.readFloatLE());
        packet.setF8(buffer.readFloatLE());
        packet.setF9(buffer.readFloatLE());
        packet.setF10(buffer.readFloatLE());
        packet.setF11(buffer.readFloatLE());
        packet.setF12(buffer.readFloatLE());
        packet.setF13(buffer.readFloatLE());
        packet.setF14(buffer.readFloatLE());
        packet.setF15(buffer.readFloatLE());
        packet.setF16(buffer.readFloatLE());

    }
}


