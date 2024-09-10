package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayStatusPacket implements BedrockPacket {
    private Status status;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
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

    @Override
    public PlayStatusPacket clone() {
        try {
            return (PlayStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

