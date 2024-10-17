package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;

import java.util.List;

@Data
public class CameraAimAssistPresetDefinition {
    private String identifier;
    private String categories;
    private final List<String> exclusionList = new ObjectArrayList<>();
    private final List<String> liquidTargetingList = new ObjectArrayList<>();
    private final List<String> itemSettings = new ObjectArrayList<>();
    private String defaultItemSettings;
    private String handSettings;
}