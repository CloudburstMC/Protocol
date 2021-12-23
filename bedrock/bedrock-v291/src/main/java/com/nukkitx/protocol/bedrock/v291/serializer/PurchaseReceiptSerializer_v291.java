package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PurchaseReceiptPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseReceiptSerializer_v291 implements BedrockPacketSerializer<PurchaseReceiptPacket> {
    public static final PurchaseReceiptSerializer_v291 INSTANCE = new PurchaseReceiptSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PurchaseReceiptPacket packet) {
        helper.writeArray(buffer, packet.getReceipts(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PurchaseReceiptPacket packet) {
        helper.readArray(buffer, packet.getReceipts(), helper::readString);
    }
}
