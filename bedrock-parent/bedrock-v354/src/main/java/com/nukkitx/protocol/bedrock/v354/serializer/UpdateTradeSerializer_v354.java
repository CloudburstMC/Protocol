package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTradeSerializer_v354 implements BedrockPacketSerializer<UpdateTradePacket> {
    public static final UpdateTradeSerializer_v354 INSTANCE = new UpdateTradeSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
        buffer.writeByte(packet.getContainerId());
        buffer.writeByte(packet.getContainerType().getId());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeInt(buffer, packet.getTradeTier());
        VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        helper.writeString(buffer, packet.getDisplayName());
        buffer.writeBoolean(packet.isNewTradingUi());
        buffer.writeBoolean(packet.isUsingEconomyTrade());
        helper.writeTag(buffer, packet.getOffers());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateTradePacket packet) {
        packet.setContainerId(buffer.readByte());
        packet.setContainerType(ContainerType.from(buffer.readByte()));
        packet.setSize(VarInts.readInt(buffer));
        packet.setTradeTier(VarInts.readInt(buffer));
        packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        packet.setDisplayName(helper.readString(buffer));
        packet.setNewTradingUi(buffer.readBoolean());
        packet.setUsingEconomyTrade(buffer.readBoolean());
        packet.setOffers(helper.readTag(buffer));
    }
}
