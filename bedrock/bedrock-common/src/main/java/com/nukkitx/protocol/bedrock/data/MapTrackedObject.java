package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3i;
import lombok.Getter;

@Getter
public class MapTrackedObject {
    private final Type type;
    private long entityId;
    private Vector3i position;

    public MapTrackedObject(long entityId) {
        this.type = Type.ENTITY;
        this.entityId = entityId;
    }

    public MapTrackedObject(Vector3i position) {
        this.type = Type.BLOCK;
        this.position = position;
    }

    public enum Type {
        ENTITY,
        BLOCK
    }
}
