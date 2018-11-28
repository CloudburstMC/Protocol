package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayStatusPacket extends BedrockPacket {
    protected Status status;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Status {
        LOGIN_SUCCESS,
        FAILED_CLIENT,
        FAILED_SERVER,
        PLAYER_SPAWN,
        FAILED_INVALID_TENANT,
        FAILED_VANILLA_EDU,
        FAILED_EDU_VANILLA,
        FAILED_SERVER_FULL
    }
}
