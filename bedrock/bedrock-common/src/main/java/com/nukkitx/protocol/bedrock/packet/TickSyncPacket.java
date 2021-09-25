package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Used to maintain synchronization with a server running in authoritative mode.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class TickSyncPacket extends BedrockPacket {
    private long requestTimestamp;
    private long responseTimestamp;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TICK_SYNC;
    }
}
