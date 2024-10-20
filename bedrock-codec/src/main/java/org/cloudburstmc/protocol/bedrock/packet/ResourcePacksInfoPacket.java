package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.*;
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

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACKS_INFO;
    }

    @Data
    @AllArgsConstructor
    public static class Entry {
        private String packId;
        private String packVersion;
        private long packSize;
        private String contentKey;
        private String subPackName;
        private String contentId;
        private boolean scripting;
        private boolean raytracingCapable;
        /**
         * @since v712
         */
        private boolean addonPack;
        /**
         * @since v748
         */
        private String cdnUrl;
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

