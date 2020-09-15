package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class MoveEntityDeltaPacket extends BedrockPacket {
    private long runtimeEntityId;

    private Vector3i movementDeltaI;
    private Vector3f movementDeltaF;

    private Vector3f rotationDelta;

    private int flags;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_ENTITY_DELTA;
    }

    @Deprecated
    public Vector3i getMovementDelta() {
        return movementDeltaI != null ? movementDeltaI : Vector3i.from(movementDeltaF.getX(), movementDeltaF.getY(), movementDeltaF.getZ());
    }

    public void setMovementDelta(Vector3i movementDelta) {
        movementDeltaI = movementDelta;
    }

    public void setMovementDelta(Vector3f movementDelta) {
        movementDeltaF = movementDelta;
    }

}
