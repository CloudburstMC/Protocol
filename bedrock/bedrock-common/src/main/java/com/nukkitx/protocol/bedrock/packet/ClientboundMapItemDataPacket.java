package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.MapDecoration;
import com.nukkitx.protocol.bedrock.data.MapTrackedObject;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientboundMapItemDataPacket extends BedrockPacket {
    private final TLongList trackedEntityIds = new TLongArrayList();
    private final List<MapTrackedObject> trackedObjects = new ArrayList<>();
    private final List<MapDecoration> decorations = new ArrayList<>();
    private long uniqueMapId;
    private int dimensionId;
    private boolean locked;
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
