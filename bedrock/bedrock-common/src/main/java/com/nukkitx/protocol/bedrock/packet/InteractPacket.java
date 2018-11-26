package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class InteractPacket extends BedrockPacket {
    protected int action;
    protected long runtimeEntityId;
    protected Vector3f mousePosition;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Action {
        NONE,
        UNKNOWN_1,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        UNKNOWN_5,
        OPEN_INVENTORY
    }
}
