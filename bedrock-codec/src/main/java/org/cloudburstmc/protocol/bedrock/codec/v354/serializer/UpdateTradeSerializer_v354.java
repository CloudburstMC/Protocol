package org.cloudburstmc.protocol.bedrock.codec.v354.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.bedrock.packet.UpdateTradePacket;
import org.cloudburstmc.protocol.common.util.TextConverter;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTradeSerializer_v354 implements BedrockPacketSerializer<UpdateTradePacket> {
    public static final UpdateTradeSerializer_v354 INSTANCE = new UpdateTradeSerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateTradePacket packet) {
        buffer.writeByte(packet.getContainerId());
        buffer.writeByte(packet.getContainerType().getId());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeInt(buffer, packet.getTradeTier());
        VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(packet.getDisplayName(CharSequence.class)));
        buffer.writeBoolean(packet.isNewTradingUi());
        buffer.writeBoolean(packet.isUsingEconomyTrade());
        helper.writeTag(buffer, packet.getOffers());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateTradePacket packet) {
        packet.setContainerId(buffer.readByte());
        packet.setContainerType(ContainerType.from(buffer.readByte()));
        packet.setSize(VarInts.readInt(buffer));
        packet.setTradeTier(VarInts.readInt(buffer));
        packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        TextConverter converter = helper.getTextConverter();
        packet.setDisplayName(converter.deserialize(helper.readString(buffer)));
        packet.setNewTradingUi(buffer.readBoolean());
        packet.setUsingEconomyTrade(buffer.readBoolean());
        packet.setOffers(helper.readTag(buffer, NbtMap.class));
    }
}
