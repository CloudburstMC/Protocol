package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameRulesChangedSerializer_v291 implements BedrockPacketSerializer<GameRulesChangedPacket> {
    public static final GameRulesChangedSerializer_v291 INSTANCE = new GameRulesChangedSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GameRulesChangedPacket packet) {
        helper.writeArray(buffer, packet.getGameRules(), helper::writeGameRule);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GameRulesChangedPacket packet) {
        helper.readArray(buffer, packet.getGameRules(), helper::readGameRule);
    }
}
