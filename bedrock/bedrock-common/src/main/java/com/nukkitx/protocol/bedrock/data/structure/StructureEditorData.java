package com.nukkitx.protocol.bedrock.data.structure;

import lombok.Value;

@Value
public class StructureEditorData {
    String name;
    String dataField;
    boolean includingPlayers;
    boolean boundingBoxVisible;
    StructureBlockType type;
    StructureSettings settings;
    StructureRedstoneSaveMode redstoneSaveMode;
}
