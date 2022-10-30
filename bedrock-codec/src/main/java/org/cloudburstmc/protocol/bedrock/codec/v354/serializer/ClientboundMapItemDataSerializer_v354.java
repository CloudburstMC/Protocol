package org.cloudburstmc.protocol.bedrock.codec.v354.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.MapDecoration;
import org.cloudburstmc.protocol.bedrock.data.MapTrackedObject;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMapItemDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundMapItemDataSerializer_v354 implements BedrockPacketSerializer<ClientboundMapItemDataPacket> {
    public static final ClientboundMapItemDataSerializer_v354 INSTANCE = new ClientboundMapItemDataSerializer_v354();

    protected static final int FLAG_TEXTURE_UPDATE = 0x02;
    protected static final int FLAG_DECORATION_UPDATE = 0x04;
    protected static final int FLAG_MAP_CREATION = 0x08;
    protected static final int FLAG_ALL = FLAG_TEXTURE_UPDATE | FLAG_DECORATION_UPDATE | FLAG_MAP_CREATION;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueMapId());

        int type = 0;
        int[] colors = packet.getColors();
        if (colors != null && colors.length > 0) {
            type |= FLAG_TEXTURE_UPDATE;
        }
        List<MapDecoration> decorations = packet.getDecorations();
        List<MapTrackedObject> trackedObjects = packet.getTrackedObjects();
        if (!decorations.isEmpty() && !trackedObjects.isEmpty()) {
            type |= FLAG_DECORATION_UPDATE;
        }
        LongList trackedEntityIds = packet.getTrackedEntityIds();
        if (!trackedEntityIds.isEmpty()) {
            type |= FLAG_MAP_CREATION;
        }

        VarInts.writeUnsignedInt(buffer, type);
        buffer.writeByte(packet.getDimensionId());
        buffer.writeBoolean(packet.isLocked());

        if ((type & FLAG_MAP_CREATION) != 0) {
            this.writeMapCreation(buffer, helper, packet);
        }

        if ((type & FLAG_ALL) != 0) {
            buffer.writeByte(packet.getScale());
        }

        if ((type & FLAG_DECORATION_UPDATE) != 0) {
            this.writeMapDecorations(buffer, helper, packet);
        }

        if ((type & FLAG_TEXTURE_UPDATE) != 0) {
            this.writeTextureUpdate(buffer, helper, packet);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        packet.setUniqueMapId(VarInts.readLong(buffer));
        int type = VarInts.readUnsignedInt(buffer);
        packet.setDimensionId(buffer.readUnsignedByte());
        packet.setLocked(buffer.readBoolean());

        if ((type & FLAG_MAP_CREATION) != 0) {
            this.readMapCreation(buffer, helper, packet);
        }

        if ((type & FLAG_ALL) != 0) {
            packet.setScale(buffer.readUnsignedByte());
        }

        if ((type & FLAG_DECORATION_UPDATE) != 0) {
            this.writeMapDecorations(buffer, helper, packet);
        }

        if ((type & FLAG_TEXTURE_UPDATE) != 0) {
            this.readTextureUpdate(buffer, helper, packet);
        }
    }

    protected void writeMapCreation(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getTrackedEntityIds().size());
        for (long trackedEntityId : packet.getTrackedEntityIds()) {
            VarInts.writeLong(buffer, trackedEntityId);
        }
    }

    protected void readMapCreation(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        LongList trackedEntityIds = packet.getTrackedEntityIds();
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            trackedEntityIds.add(VarInts.readLong(buffer));
        }
    }

    protected void writeMapDecorations(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        List<MapDecoration> decorations = packet.getDecorations();
        List<MapTrackedObject> trackedObjects = packet.getTrackedObjects();

        VarInts.writeUnsignedInt(buffer, trackedObjects.size());
        for (MapTrackedObject object : trackedObjects) {
            switch (object.getType()) {
                case BLOCK:
                    buffer.writeIntLE(object.getType().ordinal());
                    helper.writeBlockPosition(buffer, object.getPosition());
                    break;
                case ENTITY:
                    buffer.writeIntLE(object.getType().ordinal());
                    VarInts.writeLong(buffer, object.getEntityId());
                    break;
            }
        }

        VarInts.writeUnsignedInt(buffer, decorations.size());
        for (MapDecoration decoration : decorations) {
            buffer.writeByte(decoration.getImage());
            buffer.writeByte(decoration.getRotation());
            buffer.writeByte(decoration.getXOffset());
            buffer.writeByte(decoration.getYOffset());
            helper.writeString(buffer, decoration.getLabel());
            VarInts.writeUnsignedInt(buffer, decoration.getColor());
        }
    }

    protected void readMapDecorations(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        List<MapTrackedObject> trackedObjects = packet.getTrackedObjects();
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            MapTrackedObject.Type objectType = MapTrackedObject.Type.values()[buffer.readIntLE()];
            switch (objectType) {
                case BLOCK:
                    trackedObjects.add(new MapTrackedObject(helper.readBlockPosition(buffer)));
                    break;
                case ENTITY:
                    trackedObjects.add(new MapTrackedObject(VarInts.readLong(buffer)));
                    break;
            }
        }

        List<MapDecoration> decorations = packet.getDecorations();
        length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            int image = buffer.readUnsignedByte();
            int rotation = buffer.readUnsignedByte();
            int xOffset = buffer.readUnsignedByte();
            int yOffset = buffer.readUnsignedByte();
            String label = helper.readString(buffer);
            int color = VarInts.readUnsignedInt(buffer);
            decorations.add(new MapDecoration(image, rotation, xOffset, yOffset, label, color));
        }
    }

    protected void writeTextureUpdate(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        VarInts.writeInt(buffer, packet.getWidth());
        VarInts.writeInt(buffer, packet.getHeight());
        VarInts.writeInt(buffer, packet.getXOffset());
        VarInts.writeInt(buffer, packet.getYOffset());

        int length = packet.getColors().length;
        VarInts.writeUnsignedInt(buffer, length);
        for (int color : packet.getColors()) {
            VarInts.writeUnsignedInt(buffer, color);
        }
    }

    protected void readTextureUpdate(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMapItemDataPacket packet) {
        packet.setWidth(VarInts.readInt(buffer));
        packet.setHeight(VarInts.readInt(buffer));
        packet.setXOffset(VarInts.readInt(buffer));
        packet.setYOffset(VarInts.readInt(buffer));

        int length = VarInts.readUnsignedInt(buffer);
        int[] colors = new int[length];
        for (int i = 0; i < length; i++) {
            colors[i] = VarInts.readUnsignedInt(buffer);
        }
        packet.setColors(colors);
    }
}
