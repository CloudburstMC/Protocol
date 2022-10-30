package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.GameRulesChangedPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameRulesChangedSerializer_v291 implements BedrockPacketSerializer<GameRulesChangedPacket> {
    public static final GameRulesChangedSerializer_v291 INSTANCE = new GameRulesChangedSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, GameRulesChangedPacket packet) {
        helper.writeArray(buffer, packet.getGameRules(), helper::writeGameRule);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, GameRulesChangedPacket packet) {
        helper.readArray(buffer, packet.getGameRules(), helper::readGameRule);
    }
}
