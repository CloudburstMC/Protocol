package org.cloudburstmc.protocol.bedrock.data.debugshape;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

@Getter
@AllArgsConstructor
public class DebugShape {

    private final long id;
    /**
     * @since v859
     */
    private final int dimension;

    @Nullable
    private final Vector3f position;
    @Nullable
    private final Float scale;
    @Nullable
    private final Vector3f rotation;
    @Nullable
    private final Float totalTimeLeft;
    @Nullable
    private final Color color;

    public DebugShape(long id) {
        this(id, 0, null, null, null, null, null);
    }

    public DebugShape(long id, int dimension) {
        this(id, dimension, null, null, null, null, null);
    }

    public Type getType() {
        return null;
    }

    public enum Type {
        LINE,
        BOX,
        SPHERE,
        CIRCLE,
        TEXT,
        ARROW
    }
}
