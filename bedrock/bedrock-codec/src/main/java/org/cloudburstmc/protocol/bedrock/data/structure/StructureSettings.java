package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class StructureSettings {
    private final String paletteName;
    private final boolean ignoringEntities;
    private final boolean ignoringBlocks;
    private final boolean nonTickingPlayersAndTickingAreasEnabled;
    private final Vector3i size;
    private final Vector3i offset;
    private final long lastEditedByEntityId;
    private final StructureRotation rotation;
    private final StructureMirror mirror;
    private final StructureAnimationMode animationMode;
    private final float animationSeconds;
    private final float integrityValue;
    private final int integritySeed;
    private final Vector3f pivot;
}
