package com.nukkitx.protocol.bedrock.data.structure;

public enum StructureTemplateResponseType {
    NONE,
    EXPORT,
    QUERY;

    private static final StructureTemplateResponseType[] VALUES = StructureTemplateResponseType.values();

    public static StructureTemplateResponseType from(int id) {
        return VALUES[id];
    }
}
