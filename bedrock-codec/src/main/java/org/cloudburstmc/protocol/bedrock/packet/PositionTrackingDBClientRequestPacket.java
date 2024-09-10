package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PositionTrackingDBClientRequestPacket implements BedrockPacket {
    private Action action;
    private int trackingId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.POSITION_TRACKING_DB_CLIENT_REQUEST;
    }

    public enum Action {
        QUERY
    }

    @Override
    public PositionTrackingDBClientRequestPacket clone() {
        try {
            return (PositionTrackingDBClientRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

