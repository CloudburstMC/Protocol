package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.PurchaseReceiptPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseReceiptSerializer_v363 implements PacketSerializer<PurchaseReceiptPacket> {
    public static final PurchaseReceiptSerializer_v363 INSTANCE = new PurchaseReceiptSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, PurchaseReceiptPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getReceipts(), BedrockUtils::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, PurchaseReceiptPacket packet) {
        BedrockUtils.readArray(buffer, packet.getReceipts(), BedrockUtils::readString);
    }
}
