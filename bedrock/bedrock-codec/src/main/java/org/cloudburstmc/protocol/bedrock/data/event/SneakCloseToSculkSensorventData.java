package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SneakCloseToSculkSensorventData implements EventData {
    public static final SneakCloseToSculkSensorventData INSTANCE = new SneakCloseToSculkSensorventData();

    @Override
    public EventDataType getType() {
        return EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR;
    }
}
