package com.nukkitx.protocol.bedrock.data.structure;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class StructureSettings {
    String paletteName;
    boolean ignoringEntities;
    boolean ignoringBlocks;
    Vector3i size;
    Vector3i offset;
    long lastEditedByEntityId;
    StructureRotation rotation;
    StructureMirror mirror;
    StructureAnimationMode animationMode;
    float animationSeconds;
    float integrityValue;
    int integritySeed;
    Vector3f pivot;
}
