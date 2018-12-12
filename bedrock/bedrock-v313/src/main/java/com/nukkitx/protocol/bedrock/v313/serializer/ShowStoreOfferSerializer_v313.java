package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.ShowStoreOfferPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowStoreOfferSerializer_v313 implements PacketSerializer<ShowStoreOfferPacket> {
    public static final ShowStoreOfferSerializer_v313 INSTANCE = new ShowStoreOfferSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, ShowStoreOfferPacket packet) {
        BedrockUtils.writeString(buffer, packet.getOfferId());
        buffer.writeBoolean(packet.isShownToAll());
    }

    @Override
    public void deserialize(ByteBuf buffer, ShowStoreOfferPacket packet) {
        packet.setOfferId(BedrockUtils.readString(buffer));
        packet.setShownToAll(buffer.readBoolean());
    }
}
