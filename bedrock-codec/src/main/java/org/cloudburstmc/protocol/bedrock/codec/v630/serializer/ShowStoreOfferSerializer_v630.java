package org.cloudburstmc.protocol.bedrock.codec.v630.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ShowStoreOfferSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.StoreOfferRedirectType;
import org.cloudburstmc.protocol.bedrock.packet.ShowStoreOfferPacket;

public class ShowStoreOfferSerializer_v630 extends ShowStoreOfferSerializer_v291 {
    public static final ShowStoreOfferSerializer_v630 INSTANCE = new ShowStoreOfferSerializer_v630();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ShowStoreOfferPacket packet) {
        helper.writeString(buffer, packet.getOfferId());
        buffer.writeByte(packet.getRedirectType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ShowStoreOfferPacket packet) {
        packet.setOfferId(helper.readString(buffer));
        packet.setRedirectType(StoreOfferRedirectType.values()[buffer.readUnsignedByte()]);
    }
}
