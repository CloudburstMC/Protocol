package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePacksInfoPacket implements BedrockPacket {
    /**
     * @deprecated since v729
     */
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();
    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    private boolean forcedToAccept;
    /**
     * @since v662
     */
    private boolean hasAddonPacks;
    private boolean scriptingEnabled;
    /**
     * @since v448
     * @deprecated since v729
     */
    private boolean forcingServerPacksEnabled;
    /**
     * @since v618
     */
    private List<CDNEntry> CDNEntries = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACKS_INFO;
    }

    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final long packSize;
        private final String contentKey;
        private final String subPackName;
        private final String contentId;
        private final boolean scripting;
        private final boolean raytracingCapable;
        /**
         * @since v712
         */
        private final boolean addonPack;
        /**
         * @since v748
         */
        private final String cdnUrl;
    }

    @Value
    public static class CDNEntry {
        private final String packId;
        private final String remoteUrl;
    }

    @Override
    public ResourcePacksInfoPacket clone() {
        try {
            return (ResourcePacksInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

