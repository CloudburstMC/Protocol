package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetSpawnPositionPacket implements BedrockPacket {
    // spawnType is the type of spawn to set. It is either PLAYER_SPAWN or WORLD_SPAWN, and specifies
    // the behaviour of the spawn set. If WORLD_SPAWN is set, the position to which compasses will point is
    // also changed.
    private Type spawnType;

    // blockPosition is the new position of the spawn that was set. If spawnType is WORLD_SPAWN, compasses will
    // point to this position. As of 1.16, blockPosition is always the position of the player.
    private Vector3i blockPosition;

    // dimensionId is the ID of the dimension that had its spawn updated. This is specifically relevant for
    // behaviour added in 1.16 such as the respawn anchor, which allows setting the spawn in a specific
    // dimension.
    private int dimensionId;

    // SpawnPosition is a new field added in 1.16. It holds the spawn position of the world. This spawn
    // position is {-2147483648, -2147483648, -2147483648} for a default spawn position.
    private Vector3i spawnPosition = Vector3i.from(-2147483648, -2147483648, -2147483648);

    @Deprecated
    private boolean spawnForced;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SPAWN_POSITION;
    }

    public enum Type {
        PLAYER_SPAWN,
        WORLD_SPAWN
    }

    @Override
    public SetSpawnPositionPacket clone() {
        try {
            return (SetSpawnPositionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

