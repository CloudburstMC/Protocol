package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PlayerArmorDamageFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;
import java.util.Set;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayerArmorDamagePacket extends BedrockPacket {
    private final Set<PlayerArmorDamageFlag> flags = EnumSet.noneOf(PlayerArmorDamageFlag.class);
    private final int[] damage = new int[4];

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ARMOR_DAMAGE;
    }
}
