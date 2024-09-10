package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * @since v567
 */

/**
 * Deprecated since v594
 */
@Deprecated
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientCheatAbilityPacket implements BedrockPacket, PlayerAbilityHolder {

    private long uniqueEntityId;
    private PlayerPermission playerPermission;
    private CommandPermission commandPermission;
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CHEAT_ABILITY;
    }

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public ClientCheatAbilityPacket clone() {
        try {
            return (ClientCheatAbilityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

