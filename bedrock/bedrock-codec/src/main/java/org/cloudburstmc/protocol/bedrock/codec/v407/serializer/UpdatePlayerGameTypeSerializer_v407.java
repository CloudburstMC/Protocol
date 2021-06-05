package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor
public class UpdatePlayerGameTypeSerializer_v407 implements BedrockPacketSerializer<UpdatePlayerGameTypePacket> {

    public static final UpdatePlayerGameTypeSerializer_v407 INSTANCE = new UpdatePlayerGameTypeSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdatePlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGameType().ordinal());
        VarInts.writeLong(buffer, packet.getEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdatePlayerGameTypePacket packet) {
        packet.setGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setEntityId(VarInts.readLong(buffer));
    }
}
