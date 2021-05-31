package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;

@Value
public class StructureEditorData {
    private final String name;
    private final String dataField;
    private final boolean includingPlayers;
    private final boolean boundingBoxVisible;
    private final StructureBlockType type;
    private final StructureSettings settings;
    private final StructureRedstoneSaveMode redstoneSaveMode;
}
