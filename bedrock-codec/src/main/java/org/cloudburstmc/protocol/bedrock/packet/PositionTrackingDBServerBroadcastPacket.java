package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PositionTrackingDBServerBroadcastPacket implements BedrockPacket {
    private Action action;
    private int trackingId;
    private NbtMap tag;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.POSITION_TRACKING_DB_SERVER_BROADCAST;
    }

    public enum Action {
        UPDATE,
        DESTROY,
        NOT_FOUND
    }

    @Override
    public PositionTrackingDBServerBroadcastPacket clone() {
        try {
            return (PositionTrackingDBServerBroadcastPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

