package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationSeverity;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PacketViolationWarningPacket implements BedrockPacket {
    private PacketViolationType type;
    private PacketViolationSeverity severity;
    private int packetCauseId;
    private String context;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PACKET_VIOLATION_WARNING;
    }

    @Override
    public PacketViolationWarningPacket clone() {
        try {
            return (PacketViolationWarningPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

