package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetLocalPlayerAsInitializedPacket implements BedrockPacket {
    private long runtimeEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_LOCAL_PLAYER_AS_INITIALIZED;
    }

    @Override
    public SetLocalPlayerAsInitializedPacket clone() {
        try {
            return (SetLocalPlayerAsInitializedPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

