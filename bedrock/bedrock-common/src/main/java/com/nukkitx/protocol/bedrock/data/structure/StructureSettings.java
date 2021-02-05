package com.nukkitx.protocol.bedrock.data.structure;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class StructureSettings {
    private final String paletteName;
    private final boolean ignoringEntities;
    private final boolean ignoringBlocks;
    private final Vector3i size;
    private final Vector3i offset;
    private final long lastEditedByEntityId;
    private final StructureRotation rotation;
    private final StructureMirror mirror;
    private final float integrityValue;
    private final int integritySeed;
}
