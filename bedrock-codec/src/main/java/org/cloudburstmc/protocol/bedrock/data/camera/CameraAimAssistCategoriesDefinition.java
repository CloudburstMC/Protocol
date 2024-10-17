package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;

import java.util.List;

@Data
public class CameraAimAssistCategoriesDefinition {
    private String identifier;
    private final List<CameraAimAssistCategoryDefinition> categories = new ObjectArrayList<>();
}