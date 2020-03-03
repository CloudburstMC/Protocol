package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameRulesChangedSerializer_v332 implements PacketSerializer<GameRulesChangedPacket> {
    public static final GameRulesChangedSerializer_v332 INSTANCE = new GameRulesChangedSerializer_v332();

    public static final TObjectIntMap<Class<?>> RULE_TYPES = new TObjectIntHashMap<>(3, 0.5f, -1);

    static {
        RULE_TYPES.put(Boolean.class, 1);
        RULE_TYPES.put(Integer.class, 2);
        RULE_TYPES.put(Float.class, 3);
    }

    @Override
    public void serialize(ByteBuf buffer, GameRulesChangedPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getGameRules(), BedrockUtils::writeGameRule);
    }

    @Override
    public void deserialize(ByteBuf buffer, GameRulesChangedPacket packet) {
        BedrockUtils.readArray(buffer, packet.getGameRules(), BedrockUtils::readGameRule);
    }
}
