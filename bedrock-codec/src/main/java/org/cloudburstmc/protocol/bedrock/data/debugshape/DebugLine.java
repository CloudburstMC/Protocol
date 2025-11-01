package org.cloudburstmc.protocol.bedrock.data.debugshape;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class DebugLine extends DebugShape {

    Vector3f lineEndPosition;

    public DebugLine(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale, @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color, Vector3f lineEndPosition) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color);
        this.lineEndPosition = lineEndPosition;
    }

    @Override
    public Type getType() {
        return Type.LINE;
    }
}
