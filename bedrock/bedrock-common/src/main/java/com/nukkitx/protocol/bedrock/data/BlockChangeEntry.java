package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3i;
import lombok.Value;

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
