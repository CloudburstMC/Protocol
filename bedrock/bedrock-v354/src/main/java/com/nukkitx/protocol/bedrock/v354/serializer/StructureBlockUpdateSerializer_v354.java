package com.nukkitx.protocol.bedrock.v354.serializer;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.StructureEditorData;
import com.nukkitx.protocol.bedrock.data.StructureSettings;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StructureBlockUpdateSerializer_v354 implements PacketSerializer<StructureBlockUpdatePacket> {
    public static final StructureBlockUpdateSerializer_v354 INSTANCE = new StructureBlockUpdateSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        StructureEditorData editorData = packet.getEditorData();
        StructureSettings settings = editorData.getStructureSettings();

        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeUnsignedInt(buffer, editorData.getStructureBlockType());
        // Structure Editor Data start
        BedrockUtils.writeString(buffer, editorData.getName());
        BedrockUtils.writeString(buffer, editorData.getStructureDataField());
        BedrockUtils.writeBlockPosition(buffer, settings.getStructureOffset());
        BedrockUtils.writeBlockPosition(buffer, settings.getStructureSize());
        buffer.writeBoolean(!settings.isIgnoreEntities());
        buffer.writeBoolean(settings.isIgnoreBlocks());
        buffer.writeBoolean(editorData.isIncludePlayers());
        buffer.writeBoolean(false); // show air
        // Structure Settings start
        buffer.writeFloatLE(settings.getIntegrityValue());
        VarInts.writeUnsignedInt(buffer, settings.getIntegritySeed());
        VarInts.writeUnsignedInt(buffer, settings.getMirror());
        VarInts.writeUnsignedInt(buffer, settings.getRotation());
        buffer.writeBoolean(settings.isIgnoreEntities());
        buffer.writeBoolean(true); // ignore structure blocks
        Vector3i min = packet.getBlockPosition().add(settings.getStructureOffset());
        BedrockUtils.writeVector3i(buffer, min);
        Vector3i max = min.add(settings.getStructureSize());
        BedrockUtils.writeVector3i(buffer, max);
        // Structure Settings end
        // Structure Editor Data end
        buffer.writeBoolean(editorData.isShowBoundingBox());
        buffer.writeBoolean(packet.isPowered());
    }

    @Override
    public void deserialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        int structureType = VarInts.readUnsignedInt(buffer);
        // Structure Editor Data start
        String name = BedrockUtils.readString(buffer);
        String dataField = BedrockUtils.readString(buffer);
        Vector3i offset = BedrockUtils.readBlockPosition(buffer);
        Vector3i size = BedrockUtils.readBlockPosition(buffer);
        buffer.readBoolean(); // include entities
        boolean ignoreBlocks = !buffer.readBoolean();
        boolean includePlayers = buffer.readBoolean();
        buffer.readBoolean(); // show air
        // Structure Settings start
        float structureIntegrity = buffer.readFloatLE();
        int integritySeed = VarInts.readUnsignedInt(buffer);
        int mirror = VarInts.readUnsignedInt(buffer);
        int rotation = VarInts.readUnsignedInt(buffer);
        boolean ignoreEntities = buffer.readBoolean();
        buffer.readBoolean(); // ignore structure bocks
        BedrockUtils.readVector3i(buffer); // bounding box min
        BedrockUtils.readVector3i(buffer); // bounding box max
        // Structure Settings end
        // Structure Editor Data end
        boolean boundingBoxVisible = buffer.readBoolean();

        StructureSettings settings = new StructureSettings("", ignoreEntities, ignoreBlocks, size, offset,
                -1, (byte) rotation, (byte) mirror, structureIntegrity, integritySeed);
        StructureEditorData editorData = new StructureEditorData(name, dataField, includePlayers, boundingBoxVisible,
                structureType, settings);

        packet.setEditorData(editorData);
        packet.setPowered(buffer.readBoolean());
    }
}
