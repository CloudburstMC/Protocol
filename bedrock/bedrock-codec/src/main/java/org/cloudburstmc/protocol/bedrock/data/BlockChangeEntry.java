package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class BlockChangeEntry {
    Vector3i position;
    int runtimeId;
    int updateFlags;
    long messageEntityId;
    MessageType messageType;

    public enum MessageType {
        NONE,
        CREATE,
        DESTROY
    }
}
