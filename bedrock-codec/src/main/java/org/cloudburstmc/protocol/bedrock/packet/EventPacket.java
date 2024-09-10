package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EventPacket implements BedrockPacket {
    private long uniqueEntityId;
    private byte usePlayerId;
    private EventData eventData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EVENT;
    }

    public enum Event {
        ACHIEVEMENT_AWARDED,
        ENTITY_INTERACT,
        PORTAL_BUILT,
        PORTAL_USED,
        MOB_KILLED,
        CAULDRON_USED,
        PLAYER_DEATH,
        BOSS_KILLED,
        /**
         * @deprecated use {@link AgentActionEventPacket}
         */
        @Deprecated
        AGENT_COMMAND,
        AGENT_CREATED,
        PATTERN_REMOVED,
        SLASH_COMMAND_EXECUTED,
        FISH_BUCKETED,
        MOB_BORN,
        PET_DIED,
        CAULDRON_BLOCK_USED,
        COMPOSTER_BLOCK_USED,
        BELL_BLOCK_USED
    }

    @Override
    public EventPacket clone() {
        try {
            return (EventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

