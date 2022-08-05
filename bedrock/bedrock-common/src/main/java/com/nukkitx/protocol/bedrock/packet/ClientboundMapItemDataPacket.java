package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
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

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ClientboundMapItemDataPacket extends BedrockPacket {
    private final LongList trackedEntityIds = new LongArrayList();
    private final List<MapTrackedObject> trackedObjects = new ObjectArrayList<>();
    private final List<MapDecoration> decorations = new ObjectArrayList<>();
    private long uniqueMapId;
    private int dimensionId;
    private boolean locked;
    private Vector3i origin;
    private int scale;
    private int height;
    private int width;
    private int xOffset;
    private int yOffset;
    private int[] colors;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MAP_ITEM_DATA;
    }
}
