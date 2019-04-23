package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferSerializer_v354 implements PacketSerializer<TransferPacket> {
    public static final TransferSerializer_v354 INSTANCE = new TransferSerializer_v354();


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
