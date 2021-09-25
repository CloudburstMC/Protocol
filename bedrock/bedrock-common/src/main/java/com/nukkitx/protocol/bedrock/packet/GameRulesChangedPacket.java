package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class GameRulesChangedPacket extends BedrockPacket {
    private final List<GameRuleData<?>> gameRules = new ObjectArrayList<>();

    public GameRulesChangedPacket addGameRule(GameRuleData<?> gameRule) {
        this.gameRules.add(gameRule);
        return this;
    }

    public GameRulesChangedPacket addGameRules(GameRuleData<?>... gameRules) {
        this.gameRules.addAll(Arrays.asList(gameRules));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GAME_RULES_CHANGED;
    }
}
