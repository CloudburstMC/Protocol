package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;

import java.util.List;

@Data
public class CameraAimAssistCategoryDefinition {
    private String name;
    private final List<CameraAimAssistCategoryPriorities> priorities = new ObjectArrayList<>();
}