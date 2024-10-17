package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetMovementAuthorityPacket implements BedrockPacket {
    private Mode newAuthMovementMode;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_MOVEMENT_AUTHORITY;
    }

    @Override
    public SetMovementAuthorityPacket clone() {
        try {
            return (SetMovementAuthorityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum Mode {
        LEGACY_CLIENT_AUTHORITATIVE_V1,
        CLIENT_AUTHORITATIVE_V2,
        SERVER_AUTHORITATIVE_V3
    }
}