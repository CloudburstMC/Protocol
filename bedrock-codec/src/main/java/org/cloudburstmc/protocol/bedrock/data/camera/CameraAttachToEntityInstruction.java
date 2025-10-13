package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CameraAttachToEntityInstruction {

    private long uniqueEntityId;
}
