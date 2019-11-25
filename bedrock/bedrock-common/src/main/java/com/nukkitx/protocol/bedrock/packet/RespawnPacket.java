package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RespawnPacket extends BedrockPacket {
    private Vector3f position;
    private State spawnState;
    private long runtimeEntityId; // Only used server bound and pretty pointless

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum State {
        SERVER_SEARCHING,
        SERVER_READY,
        CLIENT_READY
    }
}
