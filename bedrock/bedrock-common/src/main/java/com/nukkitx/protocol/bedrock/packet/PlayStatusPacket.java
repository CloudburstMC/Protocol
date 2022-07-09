package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PlayStatusPacket extends BedrockPacket {
    private Status status;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAY_STATUS;
    }

    public enum Status {

        /**
         * Sent to confirm login success and move onto resource pack sequence
         */
        LOGIN_SUCCESS,

        /**
         * Displays outdated client disconnection screen
         */
        LOGIN_FAILED_CLIENT_OLD,

        /**
         * Displays outdated server disconnection screen
         */
        LOGIN_FAILED_SERVER_OLD,

        /**
         * Spawns player into the world
         */
        PLAYER_SPAWN,

        LOGIN_FAILED_INVALID_TENANT,

        /**
         * Sent when a Education Edition client joins an Bedrock server
         */
        LOGIN_FAILED_EDITION_MISMATCH_EDU_TO_VANILLA,

        /**
         * Sent when a Bedrock client joins an Education server
         */
        LOGIN_FAILED_EDITION_MISMATCH_VANILLA_TO_EDU,

        /**
         * Sent to a split screen player when the server is full
         */
        FAILED_SERVER_FULL_SUB_CLIENT,

        EDITOR_TO_VANILLA_MISMATCH,

        VANILLA_TO_EDITOR_MISMATCH
    }
}
