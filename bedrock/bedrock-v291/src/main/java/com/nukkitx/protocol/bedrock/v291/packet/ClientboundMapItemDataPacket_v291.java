package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.MapDecoration;
import com.nukkitx.protocol.bedrock.data.MapTrackedObject;
import com.nukkitx.protocol.bedrock.packet.ClientboundMapItemDataPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class ClientboundMapItemDataPacket_v291 extends ClientboundMapItemDataPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueMapId);

        int type = 0;
        if (colors != null && colors.length > 0) {
            type |= 0x2;
        }
        if (!decorations.isEmpty() && !trackedObjects.isEmpty()) {
            type |= 0x4;
        }
        if (!trackedEntityIds.isEmpty()) {
            type |= 0x8;
        }

        VarInts.writeUnsignedInt(buffer, type);
        buffer.writeByte(dimensionId);

        if ((type & 0x8) != 0) {
            VarInts.writeUnsignedInt(buffer, trackedEntityIds.size());
            for (long trackedEntityId : trackedEntityIds.toArray()) {
                VarInts.writeLong(buffer, trackedEntityId);
            }
        }

        if ((type & 0xe) != 0) {
            buffer.writeByte(scale);
        }

        if ((type & 0x4) != 0) {
            VarInts.writeUnsignedInt(buffer, trackedObjects.size());
            for (MapTrackedObject object : trackedObjects) {
                switch (object.getType()) {
                    case BLOCK:
                        buffer.writeIntLE(object.getType().ordinal());
                        BedrockUtils.writeBlockPosition(buffer, object.getPosition());
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
                BedrockUtils.writeString(buffer, decoration.getLabel());
                VarInts.writeUnsignedInt(buffer, decoration.getColor());
            }
        }

        if ((type & 0x2) != 0) {
            VarInts.writeInt(buffer, width);
            VarInts.writeInt(buffer, height);
            VarInts.writeInt(buffer, xOffset);
            VarInts.writeInt(buffer, yOffset);

            VarInts.writeUnsignedInt(buffer, colors.length);
            for (int color : colors) {
                VarInts.writeUnsignedInt(buffer, color);
            }
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueMapId = VarInts.readLong(buffer);
        int type = VarInts.readUnsignedInt(buffer);
        dimensionId = buffer.readUnsignedByte();

        if ((type & 0x8) != 0) {
            int length = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < length; i++) {
                trackedEntityIds.add(VarInts.readLong(buffer));
            }
        }

        if ((type & 0xe) != 0) {
            scale = buffer.readUnsignedByte();
        }

        if ((type & 0x4) != 0) {
            int length = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < length; i++) {
                MapTrackedObject.Type objectType = MapTrackedObject.Type.values()[buffer.readIntLE()];
                switch (objectType) {
                    case BLOCK:
                        trackedObjects.add(new MapTrackedObject(BedrockUtils.readBlockPosition(buffer)));
                        break;
                    case ENTITY:
                        trackedObjects.add(new MapTrackedObject(VarInts.readLong(buffer)));
                        break;
                }
            }

            length = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < length; i++) {
                int image = buffer.readUnsignedByte();
                int rotation = buffer.readUnsignedByte();
                int xOffset = buffer.readUnsignedByte();
                int yOffset = buffer.readUnsignedByte();
                String label = BedrockUtils.readString(buffer);
                int color = VarInts.readUnsignedInt(buffer);
                decorations.add(new MapDecoration(image, rotation, xOffset, yOffset, label, color));
            }
        }

        if ((type & 0x2) != 0) {
            width = VarInts.readInt(buffer);
            height = VarInts.readInt(buffer);
            xOffset = VarInts.readInt(buffer);
            yOffset = VarInts.readInt(buffer);

            int length = VarInts.readUnsignedInt(buffer);
            colors = new int[length];
            for (int i = 0; i < length; i++) {
                colors[i] = VarInts.readUnsignedInt(buffer);
            }
        }
    }
}
