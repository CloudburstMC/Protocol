package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferSerializer_v291 implements BedrockPacketSerializer<TransferPacket> {
    public static final TransferSerializer_v291 INSTANCE = new TransferSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TransferPacket packet) {
        helper.writeString(buffer, packet.getAddress());
        buffer.writeShortLE(packet.getPort());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TransferPacket packet) {
        packet.setAddress(helper.readString(buffer));
        packet.setPort(buffer.readShortLE());
    }
}
