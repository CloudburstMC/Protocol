package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.ExperimentData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackStackPacket implements BedrockPacket {
    private boolean forcedToAccept;
    private final List<Entry> behaviorPacks = new ObjectArrayList<>();
    private final List<Entry> resourcePacks = new ObjectArrayList<>();
    private String gameVersion;
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    private boolean experimentsPreviouslyToggled;
    /**
     * @since v671
     */
    private boolean hasEditorPacks;


    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_STACK;
    }

    @Value
    public static class Entry {
        private final String packId;
        private final String packVersion;
        private final String subPackName;
    }
}
