package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
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
    protected final TLongList trackedEntityIds = new TLongArrayList();
    protected final List<MapTrackedObject> trackedObjects = new ArrayList<>();
    protected final List<MapDecoration> decorations = new ArrayList<>();
    protected long uniqueMapId;
    protected int dimensionId;
    protected int scale;
    protected int height;
    protected int width;
    protected int xOffset;
    protected int yOffset;
    protected int[] colors;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
