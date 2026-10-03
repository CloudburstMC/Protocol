package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetScoreboardIdentityPacket implements BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SCOREBOARD_IDENTITY;
    }

    public enum Action {
        ADD,
        REMOVE
    }

    @Data
    public static class Entry {
        long scoreboardId;
        /**
         * Unique entity id of the player, sent for {@link Action#ADD} only.
         */
        long playerId;
        /**
         * @deprecated never sent; use {@link #playerId}
         */
        UUID uuid;

        public Entry(long scoreboardId, UUID uuid) {
            this.scoreboardId = scoreboardId;
            this.uuid = uuid;
        }

        public Entry(long scoreboardId, long playerId) {
            this.scoreboardId = scoreboardId;
            this.playerId = playerId;
        }
    }

    @Override
    public SetScoreboardIdentityPacket clone() {
        try {
            return (SetScoreboardIdentityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

