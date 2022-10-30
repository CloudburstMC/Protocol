package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetPlayerGameTypePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetPlayerGameTypeSerializer_v291 implements BedrockPacketSerializer<SetPlayerGameTypePacket> {
    public static final SetPlayerGameTypeSerializer_v291 INSTANCE = new SetPlayerGameTypeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGamemode());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetPlayerGameTypePacket packet) {
        packet.setGamemode(VarInts.readInt(buffer));
    }
}
