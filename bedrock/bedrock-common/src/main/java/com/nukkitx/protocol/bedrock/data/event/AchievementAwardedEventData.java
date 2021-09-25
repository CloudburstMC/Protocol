package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class AchievementAwardedEventData implements EventData {
    int achievementId;

    @Override
    public EventDataType getType() {
        return EventDataType.ACHIEVEMENT_AWARDED;
    }
}
