package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ShowStoreOfferPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ShowStoreOfferPacket_v291 extends ShowStoreOfferPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, offerId);
        buffer.writeBoolean(shownToAll);
    }

    @Override
    public void decode(ByteBuf buffer) {
        offerId = BedrockUtils.readString(buffer);
        shownToAll = buffer.readBoolean();
    }
}
