package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class AchievementAwardedEventData implements EventData {
    private final int achievementId;

    @Override
    public EventDataType getType() {
        return EventDataType.ACHIEVEMENT_AWARDED;
    }
}
