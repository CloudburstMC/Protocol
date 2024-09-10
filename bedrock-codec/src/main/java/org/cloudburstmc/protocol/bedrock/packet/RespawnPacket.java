package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RespawnPacket implements BedrockPacket {
    private Vector3f position;
    private State state;
    private long runtimeEntityId; // Only used server bound and pretty pointless

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESPAWN;
    }

    public enum State {
        SERVER_SEARCHING,
        SERVER_READY,
        CLIENT_READY
    }

    @Override
    public RespawnPacket clone() {
        try {
            return (RespawnPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

