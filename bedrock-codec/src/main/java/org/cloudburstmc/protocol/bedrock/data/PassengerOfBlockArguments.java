package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class PassengerOfBlockArguments {

    Vector3i blockPos;
    Vector3f offset;
    float rotation;
    float rotationLimit;
    EmoteType emoteType;

    public enum EmoteType {
        STANDING,
        RIDING,
        LAYING
    }
}
