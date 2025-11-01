package org.cloudburstmc.protocol.bedrock.data.debugshape;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class DebugText extends DebugShape {

    String text;

    public DebugText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale, @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color, String text) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color);
        this.text = text;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
