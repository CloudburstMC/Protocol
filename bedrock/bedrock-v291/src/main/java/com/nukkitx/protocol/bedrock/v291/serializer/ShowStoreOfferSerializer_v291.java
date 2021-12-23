package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ShowStoreOfferPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowStoreOfferSerializer_v291 implements BedrockPacketSerializer<ShowStoreOfferPacket> {
    public static final ShowStoreOfferSerializer_v291 INSTANCE = new ShowStoreOfferSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowStoreOfferPacket packet) {
        helper.writeString(buffer, packet.getOfferId());
        buffer.writeBoolean(packet.isShownToAll());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowStoreOfferPacket packet) {
        packet.setOfferId(helper.readString(buffer));
        packet.setShownToAll(buffer.readBoolean());
    }
}
