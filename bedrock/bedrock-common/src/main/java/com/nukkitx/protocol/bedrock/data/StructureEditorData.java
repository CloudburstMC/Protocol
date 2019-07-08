package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class StructureEditorData {
    private final String name;
    private final String structureDataField;
    private final boolean includePlayers;
    private final boolean showBoundingBox;
    private final int structureBlockType;
    private final StructureSettings structureSettings;
}
