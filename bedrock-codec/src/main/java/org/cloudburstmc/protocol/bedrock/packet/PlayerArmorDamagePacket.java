package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.PlayerArmorDamageFlag;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.EnumSet;
import java.util.Set;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerArmorDamagePacket implements BedrockPacket {
    private final Set<PlayerArmorDamageFlag> flags = EnumSet.noneOf(PlayerArmorDamageFlag.class);
    private final int[] damage = new int[5];

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ARMOR_DAMAGE;
    }

    @Override
    public PlayerArmorDamagePacket clone() {
        try {
            return (PlayerArmorDamagePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

