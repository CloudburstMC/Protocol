package org.cloudburstmc.protocol.bedrock.data.structure;

public enum StructureTemplateRequestOperation {
    NONE,
    EXPORT_FROM_SAVED_MODE,
    EXPORT_FROM_LOAD_MODE,
    QUERY_SAVED_STRUCTURE,
    /**
     * @since v560
     * @deprecated since v712
     */
    IMPORT;

    private static final StructureTemplateRequestOperation[] VALUES = StructureTemplateRequestOperation.values();

    public static StructureTemplateRequestOperation from(int id) {
        return VALUES[id];
    }
}
