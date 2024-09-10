package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AdventureSetting;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.EnumSet;
import java.util.Set;

/**
 * @deprecated Removed in 1.19.30 (553). Use {@link UpdateAbilitiesPacket} and {@link UpdateAdventureSettingsPacket} instead.
 */
@Data
@Deprecated
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AdventureSettingsPacket implements BedrockPacket {
    private final Set<AdventureSetting> settings = EnumSet.noneOf(AdventureSetting.class);
    private CommandPermission commandPermission = CommandPermission.ANY;
    private PlayerPermission playerPermission = PlayerPermission.VISITOR;
    private long uniqueEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADVENTURE_SETTINGS;
    }

    @Override
    public AdventureSettingsPacket clone() {
        try {
            return (AdventureSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

