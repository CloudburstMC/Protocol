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
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();
    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    private boolean forcedToAccept;
    private boolean scriptingEnabled;
    /**
     * @since v448
     */
    private boolean forcingServerPacksEnabled;

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
    }
}
