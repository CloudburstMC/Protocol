package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.MapDecoration;
import org.cloudburstmc.protocol.bedrock.data.MapTrackedObject;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundMapItemDataPacket implements BedrockPacket {
    private final LongList trackedEntityIds = new LongArrayList();
    private final List<MapTrackedObject> trackedObjects = new ObjectArrayList<>();
    private final List<MapDecoration> decorations = new ObjectArrayList<>();
    private long uniqueMapId;
    private int dimensionId;
    private boolean locked;
    /**
     * The world-relative position of the map's origin.
     *
     * @since 1.19.20
     */
    private Vector3i origin;
    private int scale;
    private int height;
    private int width;
    private int xOffset;
    private int yOffset;
    private int[] colors;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MAP_ITEM_DATA;
    }

    @Override
    public ClientboundMapItemDataPacket clone() {
        try {
            return (ClientboundMapItemDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

