package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.PredictionType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent to the client when the server's movement prediction system does not match what the client is sending.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CorrectPlayerMovePredictionPacket implements BedrockPacket {

    /**
     * Client's reported position by the server
     *
     * @param position reported position
     * @return reported position
     */
    private Vector3f position;

    /**
     * Difference in client and server prediction
     *
     * @param delta position difference
     * @return position difference
     */
    private Vector3f delta;

    /**
     * If the client is on the ground. (Not falling or jumping)
     *
     * @param onGround is client on the ground
     * @return is client on the ground
     */
    private boolean onGround;

    /**
     * The tick which is being corrected by the server.
     *
     * @param tick to be corrected
     * @return to be corrected
     */
    private long tick;

    /**
     * @since 649
     *
     * The type of prediction player sends.
     */
    private PredictionType predictionType = PredictionType.PLAYER;

    /**
     * @since 671
     *
     * The rotation of the vehicle.
     */
    private Vector2f vehicleRotation;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CORRECT_PLAYER_MOVE_PREDICTION;
    }
}
