package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovePlayerPacket extends BedrockPacket {
    protected long runtimeEntityId;
    protected Vector3f position;
    protected Vector3f rotation;
    protected Mode mode;
    protected boolean onGround;
    protected long ridingRuntimeEntityId;
    protected TeleportationCause teleportationCause;
    protected int unknown0;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Mode {
        NORMAL,
        RESET,
        TELEPORT,
        ROTATION
    }

    public enum TeleportationCause {
        UNKNOWN,
        PROJECTILE,
        CHORUS_FRUIT,
        COMMAND,
        BEHAVIOR,
        COUNT
    }
}
