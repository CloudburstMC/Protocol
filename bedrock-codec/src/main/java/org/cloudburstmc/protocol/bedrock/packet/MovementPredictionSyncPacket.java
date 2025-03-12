package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.Set;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MovementPredictionSyncPacket implements BedrockPacket {
    private long runtimeEntityId;

    private final Set<EntityFlag> flags = new ObjectOpenHashSet<>();
    private Vector3f boundingBox;

    private float speed;
    private float underwaterSpeed;
    private float lavaSpeed;
    private float jumpStrength;
    private float health;
    private float hunger;
    /**
     * @since v786
     */
    private boolean actorFlyingState;


    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVEMENT_PREDICTION_SYNC;
    }

    @Override
    public MovementPredictionSyncPacket clone() {
        try {
            return (MovementPredictionSyncPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

