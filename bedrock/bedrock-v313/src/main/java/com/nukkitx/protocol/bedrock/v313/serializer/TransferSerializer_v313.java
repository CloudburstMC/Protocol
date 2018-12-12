package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferSerializer_v313 implements PacketSerializer<TransferPacket> {
    public static final TransferSerializer_v313 INSTANCE = new TransferSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, TransferPacket packet) {
        BedrockUtils.writeString(buffer, packet.getAddress());
        buffer.writeShortLE(packet.getPort());
    }

    @Override
    public void deserialize(ByteBuf buffer, TransferPacket packet) {
        packet.setAddress(BedrockUtils.readString(buffer));
        packet.setPort(buffer.readUnsignedShortLE());
    }
}
