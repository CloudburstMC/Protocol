package org.cloudburstmc.protocol.bedrock.data.structure;

public enum StructureAnimationMode {
    NONE,
    LAYER,
    BLOCKS;

    private static final StructureAnimationMode[] VALUES = StructureAnimationMode.values();

    public static StructureAnimationMode from(int id) {
        return VALUES[id];
    }
}
