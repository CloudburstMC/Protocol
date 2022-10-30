package org.cloudburstmc.protocol.bedrock.data.structure;

public enum StructureBlockType {
    DATA,
    SAVE,
    LOAD,
    CORNER,
    INVALID,
    EXPORT;

    private static final StructureBlockType[] VALUES = StructureBlockType.values();

    public static StructureBlockType from(int id) {
        return VALUES[id];
    }
}
