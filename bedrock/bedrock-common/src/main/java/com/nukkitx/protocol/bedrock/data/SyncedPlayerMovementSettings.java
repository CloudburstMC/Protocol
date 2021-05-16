package com.nukkitx.protocol.bedrock.data;

import lombok.Data;

/**
 * Stores server authoritative flags that have been set on the server.
 */
@Data
public class SyncedPlayerMovementSettings {
    private AuthoritativeMovementMode movementMode;
    private int rewindHistorySize;
    boolean serverAuthoritativeBlockBreaking;
}
