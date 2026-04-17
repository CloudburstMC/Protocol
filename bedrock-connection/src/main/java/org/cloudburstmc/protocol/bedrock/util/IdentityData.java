package org.cloudburstmc.protocol.bedrock.util;

import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

@ToString
public final class IdentityData {
    public final String displayName;
    /**
     * Identity UUID, derived from the XUID when online, or from the username when offline.
     * @deprecated v818: Use {@link #minecraftId} instead.
     */
    @Nullable
    public final UUID identity;
    public final String xuid;
    public final @Nullable String titleId;
    /**
     * The player's Minecraft PlayFab ID
     * @since v818
     */
    @Nullable
    public final String minecraftId;

    public IdentityData(String displayName, UUID identity, String xuid, @Nullable String titleId, @Nullable String minecraftId) {
        this.displayName = displayName;
        this.identity = identity;
        this.xuid = xuid;
        this.titleId = titleId;
        this.minecraftId = minecraftId;
    }
}
