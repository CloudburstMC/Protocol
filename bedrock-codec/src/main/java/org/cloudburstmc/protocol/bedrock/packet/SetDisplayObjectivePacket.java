package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetDisplayObjectivePacket implements BedrockPacket {
    private String displaySlot;
    private String objectiveId;
    private String displayName;
    private String criteria;
    private int sortOrder;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_DISPLAY_OBJECTIVE;
    }

    @Override
    public SetDisplayObjectivePacket clone() {
        try {
            return (SetDisplayObjectivePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

