package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

@Value
public class BlockChangeEntry {
    Vector3i position;
    BlockDefinition definition;
    int updateFlags;
    long messageEntityId;
    MessageType messageType;

    public enum MessageType {
        NONE,
        CREATE,
        DESTROY
    }
}
