package org.cloudburstmc.protocol.bedrock.data.structure;

public enum StructureRotation {
    NONE,
    ROTATE_90,
    ROTATE_180,
    ROTATE_270;

    private static final StructureRotation[] VALUES = StructureRotation.values();

    public static StructureRotation from(int id) {
        return VALUES[id];
    }
}
