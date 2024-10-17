package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.Data;

@Data
public class CameraAimAssistCategoryPriorities {
    private int entities;
    private int blocks;
    private int entityDefault;
    private int blockDefault;
}