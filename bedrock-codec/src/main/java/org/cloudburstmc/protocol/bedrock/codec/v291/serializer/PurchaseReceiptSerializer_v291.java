package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PurchaseReceiptPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseReceiptSerializer_v291 implements BedrockPacketSerializer<PurchaseReceiptPacket> {
    public static final PurchaseReceiptSerializer_v291 INSTANCE = new PurchaseReceiptSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PurchaseReceiptPacket packet) {
        helper.writeArray(buffer, packet.getReceipts(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PurchaseReceiptPacket packet) {
        helper.readArray(buffer, packet.getReceipts(), buf -> helper.readStringMaxLen(buf, 1024 * 128)); // This json is usually bigger than other strings
    }
}
