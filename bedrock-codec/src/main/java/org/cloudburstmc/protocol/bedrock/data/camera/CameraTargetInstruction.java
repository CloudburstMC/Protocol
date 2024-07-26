package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector3f;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraTargetInstruction {
    private Vector3f targetCenterOffset;
    private long uniqueEntityId;
}