package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddHangingEntityPacket extends BedrockPacket {
    protected long uniqueEntityId;
    protected long runtimeEntityId;
    protected Vector3i blockPosition;
    protected int rotation;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
