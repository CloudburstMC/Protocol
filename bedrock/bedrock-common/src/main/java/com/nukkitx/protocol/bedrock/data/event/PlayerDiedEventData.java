package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PlayerDiedEventData implements EventData {
    int attackerEntityId;
    int attackerVariant;
    int entityDamageCause;
    boolean inRaid;

    @Override
    public EventDataType getType() {
        return EventDataType.PLAYER_DIED;
    }
}