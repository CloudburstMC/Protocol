package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3i;
import lombok.Value;

@Value
public class StructureSettings {
    private final String paletteName;
    private final boolean ignoreEntities;
    private final boolean ignoreBlocks;
    private final Vector3i structureSize;
    private final Vector3i structureOffset;
    private final long lastTouchedByEntityId;
    private final byte rotation;
    private final byte mirror;
    private final float integrityValue;
    private final int integritySeed;
}
