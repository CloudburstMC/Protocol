package org.cloudburstmc.protocol.bedrock.codec.v313.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.bedrock.packet.UpdateTradePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTradeSerializer_v313 implements BedrockPacketSerializer<UpdateTradePacket> {
    public static final UpdateTradeSerializer_v313 INSTANCE = new UpdateTradeSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateTradePacket packet) {
        buffer.writeByte(packet.getContainerId());
        buffer.writeByte(packet.getContainerType().getId());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeInt(buffer, packet.isNewTradingUi() ? 40 : 0);
        VarInts.writeInt(buffer, packet.getTradeTier());
        buffer.writeBoolean(packet.isRecipeAddedOnUpdate());
        VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        helper.writeString(buffer, packet.getDisplayName());
        helper.writeTag(buffer, packet.getOffers());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateTradePacket packet) {
        packet.setContainerId(buffer.readByte());
        packet.setContainerType(ContainerType.from(buffer.readByte()));
        packet.setSize(VarInts.readInt(buffer));
        packet.setNewTradingUi(VarInts.readInt(buffer) >= 40);
        packet.setTradeTier(VarInts.readInt(buffer));
        packet.setRecipeAddedOnUpdate(buffer.readBoolean());
        packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        packet.setDisplayName(helper.readString(buffer));
        packet.setOffers(helper.readTag(buffer, NbtMap.class));
    }
}
