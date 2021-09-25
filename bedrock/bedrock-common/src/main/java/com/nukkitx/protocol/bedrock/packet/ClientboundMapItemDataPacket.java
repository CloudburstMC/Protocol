package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.MapDecoration;
import com.nukkitx.protocol.bedrock.data.MapTrackedObject;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ClientboundMapItemDataPacket extends BedrockPacket {
    private final LongList trackedEntityIds = new LongArrayList();
    private final List<MapTrackedObject> trackedObjects = new ObjectArrayList<>();
    private final List<MapDecoration> decorations = new ObjectArrayList<>();
    private long uniqueMapId;
    private int dimensionId;
    private boolean locked;
    private int scale;
    private int height;
    private int width;
    private int xOffset;
    private int yOffset;
    private int[] colors;

    public ClientboundMapItemDataPacket addTrackedEntityId(long trackedEntityId) {
        this.trackedEntityIds.add(trackedEntityId);
        return this;
    }

    public ClientboundMapItemDataPacket addTrackedEntityIds(long... trackedEntityIds) {
        for (long trackedEntityId : trackedEntityIds) {
            this.trackedEntityIds.add(trackedEntityId);
        }
        return this;
    }

    public ClientboundMapItemDataPacket addTrackedObject(MapTrackedObject trackedObject) {
        this.trackedObjects.add(trackedObject);
        return this;
    }

    public ClientboundMapItemDataPacket addTrackedObjects(MapTrackedObject... trackedObjects) {
        this.trackedObjects.addAll(Arrays.asList(trackedObjects));
        return this;
    }

    public ClientboundMapItemDataPacket addDecoration(MapDecoration decoration) {
        this.decorations.add(decoration);
        return this;
    }

    public ClientboundMapItemDataPacket addTrackedObjects(MapDecoration... decorations) {
        this.decorations.addAll(Arrays.asList(decorations));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MAP_ITEM_DATA;
    }
}
