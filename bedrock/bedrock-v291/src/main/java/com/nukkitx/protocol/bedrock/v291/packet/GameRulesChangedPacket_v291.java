package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.netty.buffer.ByteBuf;

public class GameRulesChangedPacket_v291 extends GameRulesChangedPacket {
    public static final TObjectIntMap<Class> RULE_TYPES = new TObjectIntHashMap<>(3, 0.5f, -1);

    static {
        RULE_TYPES.put(Boolean.class, 1);
        RULE_TYPES.put(Integer.class, 2);
        RULE_TYPES.put(Float.class, 3);
    }

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeArray(buffer, gameRules, BedrockUtils::writeGameRule);
    }

    @Override
    public void decode(ByteBuf buffer) {
        BedrockUtils.readArray(buffer, gameRules, BedrockUtils::readGameRule);
    }
}
