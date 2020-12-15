package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UpdatePlayerGameTypeSerializer_v407 implements BedrockPacketSerializer<UpdatePlayerGameTypePacket> {

    public static final UpdatePlayerGameTypeSerializer_v407 INSTANCE = new UpdatePlayerGameTypeSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdatePlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGameType().ordinal());
        VarInts.writeLong(buffer, packet.getEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdatePlayerGameTypePacket packet) {
        packet.setGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setEntityId(VarInts.readLong(buffer));
    }
}
