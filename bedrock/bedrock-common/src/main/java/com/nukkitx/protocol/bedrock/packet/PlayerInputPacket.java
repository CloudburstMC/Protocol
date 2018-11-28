package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector2f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerInputPacket extends BedrockPacket {
    protected Vector2f inputMotion;
    protected boolean jumping;
    protected boolean sneaking;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
