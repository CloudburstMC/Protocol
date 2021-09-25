package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Sent to the client when the server's movement prediction system does not match what the client is sending.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CorrectPlayerMovePredictionPacket extends BedrockPacket {

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

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CORRECT_PLAYER_MOVE_PREDICTION;
    }
}
