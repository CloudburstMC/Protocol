package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MoveEntityAbsolutePacket extends BedrockPacket {
    protected long runtimeEntityId;
    protected Vector3f position;
    protected Vector3f rotation;
    protected boolean onGround;
    protected boolean teleported;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
