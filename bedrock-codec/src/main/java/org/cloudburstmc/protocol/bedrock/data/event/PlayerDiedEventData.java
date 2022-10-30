package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PlayerDiedEventData implements EventData {
    private final int attackerEntityId;
    private final int attackerVariant;
    private final int entityDamageCause;
    private final boolean inRaid;

    @Override
    public EventDataType getType() {
        return EventDataType.PLAYER_DIED;
    }
}