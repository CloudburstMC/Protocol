package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PlayerDiedEventData implements EventData {
    private final int unknown0;
    private final int unknown1;
    private final int entityDamageCause;
    private final boolean inRaid;

    @Override
    public EventDataType getType() {
        return EventDataType.PLAYER_DIED;
    }
}