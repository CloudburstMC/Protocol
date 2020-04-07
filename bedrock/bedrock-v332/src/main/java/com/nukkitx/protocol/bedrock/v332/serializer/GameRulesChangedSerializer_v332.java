package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameRulesChangedSerializer_v332 implements PacketSerializer<GameRulesChangedPacket> {
    public static final GameRulesChangedSerializer_v332 INSTANCE = new GameRulesChangedSerializer_v332();

    public static final Object2IntMap<Class<?>> RULE_TYPES = new Object2IntOpenHashMap<>(3, 0.5f);

    static {
        RULE_TYPES.defaultReturnValue(-1);
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
