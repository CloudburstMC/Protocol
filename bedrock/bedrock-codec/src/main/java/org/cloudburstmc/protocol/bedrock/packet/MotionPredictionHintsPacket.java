package org.cloudburstmc.protocol.bedrock.packet;


import com.nukkitx.math.vector.Vector3f;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Extension of the {@link SetEntityMotionPacket} which adds the {@link #onGround} field.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MotionPredictionHintsPacket implements BedrockPacket {

    /**
     * The runtime ID of the entity to set motion.
     *
     * @param runtimeEntityId runtime ID
     * @return runtime ID
     */
    private long runtimeEntityId;

    /**
     * Motion to set onto the specified entity
     *
     * @param motion motion of entity
     * @return motion of entity
     */
    private Vector3f motion;

    /**
     * If the entity is on the ground. (Not falling or jumping)
     *
     * @param onGround is entity on the ground
     * @return is entity on the ground
     */
    private boolean onGround;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_ENTITY_MOTION_PLUS;
    }
}
