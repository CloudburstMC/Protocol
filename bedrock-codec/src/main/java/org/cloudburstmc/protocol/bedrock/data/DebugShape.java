package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

@Value
public class DebugShape {

    long id;
    @Nullable
    Type type;
    @Nullable
    Vector3f position;
    @Nullable
    Float scale;
    @Nullable
    Vector3f rotation;
    @Nullable
    Float totalTimeLeft;
    @Nullable
    Color color;
    @Nullable
    String text;
    @Nullable
    Vector3f boxBounds;
    @Nullable
    Vector3f lineEndPosition;
    @Nullable
    Float arrowHeadLength;
    @Nullable
    Float arrowHeadRadius;
    @Nullable
    Integer segments;

    public enum Type {
        LINE,
        BOX,
        SPHERE,
        CIRCLE,
        TEXT,
        ARROW
    }
}
