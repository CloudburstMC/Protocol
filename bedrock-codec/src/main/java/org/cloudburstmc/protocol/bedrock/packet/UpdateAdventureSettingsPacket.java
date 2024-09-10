package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAdventureSettingsPacket implements BedrockPacket {
    private boolean noPvM;
    private boolean noMvP;
    private boolean immutableWorld;
    private boolean showNameTags;
    private boolean autoJump;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_ADVENTURE_SETTINGS;
    }

    @Override
    public UpdateAdventureSettingsPacket clone() {
        try {
            return (UpdateAdventureSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

