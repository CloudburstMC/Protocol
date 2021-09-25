package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PlayerArmorDamageFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerArmorDamagePacket extends BedrockPacket {
    private final Set<PlayerArmorDamageFlag> flags = EnumSet.noneOf(PlayerArmorDamageFlag.class);
    private final int[] damage = new int[4];

    public PlayerArmorDamagePacket addFlag(PlayerArmorDamageFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public PlayerArmorDamagePacket addFlags(PlayerArmorDamageFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public PlayerArmorDamagePacket setDamage(int index, int damage) {
        this.damage[index] = damage;
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ARMOR_DAMAGE;
    }
}
