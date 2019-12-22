package com.nukkitx.protocol.bedrock.data.event;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import lombok.Value;

@Value
public class MovementEventData implements EventData {
    private final int movementType;
    private final Vector2f rotation; // Guess
    private final Vector3f position; // Guess

    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT;
    }
}
