package com.nukkitx.protocol.bedrock.v544.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.MapDecoration;
import com.nukkitx.protocol.bedrock.data.MapTrackedObject;
import com.nukkitx.protocol.bedrock.packet.ClientboundMapItemDataPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundMapItemDataSerializer_v544 implements BedrockPacketSerializer<ClientboundMapItemDataPacket> {
    public static final ClientboundMapItemDataSerializer_v544 INSTANCE = new ClientboundMapItemDataSerializer_v544();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientboundMapItemDataPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueMapId());

        int type = 0;
        int[] colors = packet.getColors();
        if (colors != null && colors.length > 0) {
            type |= 0x2;
        }
        List<MapDecoration> decorations = packet.getDecorations();
        List<MapTrackedObject> trackedObjects = packet.getTrackedObjects();
        if (!decorations.isEmpty() && !trackedObjects.isEmpty()) {
            type |= 0x4;
        }
        LongList trackedEntityIds = packet.getTrackedEntityIds();
        if (!trackedEntityIds.isEmpty()) {
            type |= 0x8;
        }

        VarInts.writeUnsignedInt(buffer, type);
        buffer.writeByte(packet.getDimensionId());
        buffer.writeBoolean(packet.isLocked());
        helper.writeBlockPosition(buffer, packet.getOrigin()); // <== Addition

        if ((type & 0x8) != 0) {
            VarInts.writeUnsignedInt(buffer, trackedEntityIds.size());
            for (long trackedEntityId : trackedEntityIds) {
                VarInts.writeLong(buffer, trackedEntityId);
            }
        }

        if ((type & 0xe) != 0) {
            buffer.writeByte(packet.getScale());
        }

        if ((type & 0x4) != 0) {
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

        if ((type & 0x2) != 0) {
            VarInts.writeInt(buffer, packet.getWidth());
            VarInts.writeInt(buffer, packet.getHeight());
            VarInts.writeInt(buffer, packet.getXOffset());
            VarInts.writeInt(buffer, packet.getYOffset());

            VarInts.writeUnsignedInt(buffer, colors.length);
            for (int color : colors) {
                VarInts.writeUnsignedInt(buffer, color);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientboundMapItemDataPacket packet) {
        packet.setUniqueMapId(VarInts.readLong(buffer));
        int type = VarInts.readUnsignedInt(buffer);
        packet.setDimensionId(buffer.readUnsignedByte());
        packet.setLocked(buffer.readBoolean());
        packet.setOrigin(helper.readBlockPosition(buffer)); // <== Addition

        if ((type & 0x8) != 0) {
            LongList trackedEntityIds = packet.getTrackedEntityIds();
            int length = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < length; i++) {
                trackedEntityIds.add(VarInts.readLong(buffer));
            }
        }

        if ((type & 0xe) != 0) {
            packet.setScale(buffer.readUnsignedByte());
        }

        if ((type & 0x4) != 0) {
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

        if ((type & 0x2) != 0) {
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
}
