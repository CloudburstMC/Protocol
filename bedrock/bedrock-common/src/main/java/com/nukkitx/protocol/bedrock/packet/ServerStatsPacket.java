package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Stats sent to the client regarding the server's network performance
 * that are used for telemetry.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ServerStatsPacket extends BedrockPacket {
    private float serverTime;
    private float networkTime;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_STATS;
    }
}
