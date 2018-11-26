package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.PurchaseReceiptPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class PurchaseReceiptPacket_v291 extends PurchaseReceiptPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeArray(buffer, receipts, BedrockUtils::writeString);
    }

    @Override
    public void decode(ByteBuf buffer) {
        BedrockUtils.readArray(buffer, receipts, BedrockUtils::readString);
    }
}
