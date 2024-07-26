package org.cloudburstmc.protocol.bedrock.data.structure;

public enum StructureTemplateResponseType {
    NONE,
    EXPORT,
    QUERY,
    /**
     * @since v560
     * @deprecated since v712
     */
    IMPORT;

    private static final StructureTemplateResponseType[] VALUES = StructureTemplateResponseType.values();

    public static StructureTemplateResponseType from(int id) {
        return VALUES[id];
    }
}
