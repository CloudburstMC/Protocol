package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ShowStoreOfferPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowStoreOfferSerializer_v291 implements BedrockPacketSerializer<ShowStoreOfferPacket> {
    public static final ShowStoreOfferSerializer_v291 INSTANCE = new ShowStoreOfferSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ShowStoreOfferPacket packet) {
        helper.writeString(buffer, packet.getOfferId());
        buffer.writeBoolean(packet.isShownToAll());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ShowStoreOfferPacket packet) {
        packet.setOfferId(helper.readString(buffer));
        packet.setShownToAll(buffer.readBoolean());
    }
}
