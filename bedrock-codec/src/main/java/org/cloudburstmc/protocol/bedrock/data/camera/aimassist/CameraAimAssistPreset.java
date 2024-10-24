package org.cloudburstmc.protocol.bedrock.data.camera.aimassist;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class CameraAimAssistPreset {
    private String identifier;
    private String categories;
    private final List<String> exclusionList = new ObjectArrayList<>();
    private final List<String> liquidTargetingList = new ObjectArrayList<>();
    private final List<ItemSettings> itemSettings = new ObjectArrayList<>();
    private String defaultItemSettings;
    private String handSettings;

    @Value
    public static class ItemSettings {
        String itemIdentifier;
        String categoryName;
    }
}