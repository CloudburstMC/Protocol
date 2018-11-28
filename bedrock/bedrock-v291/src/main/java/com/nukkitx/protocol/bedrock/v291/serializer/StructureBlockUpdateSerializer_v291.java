package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StructureBlockUpdateSerializer_v291 implements PacketSerializer<StructureBlockUpdatePacket> {
    public static final StructureBlockUpdateSerializer_v291 INSTANCE = new StructureBlockUpdateSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeUnsignedInt(buffer, packet.getStructureType().ordinal());
        // Structure Editor Data start
        BedrockUtils.writeString(buffer, packet.getCustomName());
        BedrockUtils.writeString(buffer, packet.getMetadata());
        BedrockUtils.writeBlockPosition(buffer, packet.getStructureOffset());
        BedrockUtils.writeBlockPosition(buffer, packet.getStructureSize());
        buffer.writeBoolean(packet.isIncludingEntities());
        buffer.writeBoolean(packet.isIgnoringBlocks());
        buffer.writeBoolean(packet.isIncludingPlayers());
        buffer.writeBoolean(packet.isShowingAir());
        // Structure Settings start
        buffer.writeFloatLE(packet.getIntegrity());
        VarInts.writeUnsignedInt(buffer, packet.getSeed());
        VarInts.writeUnsignedInt(buffer, packet.getMirror());
        VarInts.writeUnsignedInt(buffer, packet.getRotation());
        buffer.writeBoolean(packet.isIgnoringEntities());
        buffer.writeBoolean(packet.isIgnoringStructureBlocks());
        BedrockUtils.writeVector3i(buffer, packet.getBbMin());
        BedrockUtils.writeVector3i(buffer, packet.getBbMax());
        // Structure Settings end
        // Structure Editor Data end
        buffer.writeBoolean(packet.isBoundingBoxVisible());
        buffer.writeBoolean(packet.isPowered());
    }

    @Override
    public void deserialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setStructureType(Type.values()[VarInts.readUnsignedInt(buffer)]);
        // Structure Editor Data start
        packet.setCustomName(BedrockUtils.readString(buffer));
        packet.setMetadata(BedrockUtils.readString(buffer));
        packet.setStructureOffset(BedrockUtils.readBlockPosition(buffer));
        packet.setStructureSize(BedrockUtils.readBlockPosition(buffer));
        packet.setIncludingEntities(buffer.readBoolean());
        packet.setIgnoringBlocks(buffer.readBoolean());
        packet.setIncludingPlayers(buffer.readBoolean());
        packet.setShowingAir(buffer.readBoolean());
        // Structure Settings start
        packet.setIntegrity(buffer.readFloatLE());
        packet.setSeed(VarInts.readUnsignedInt(buffer));
        packet.setMirror(VarInts.readUnsignedInt(buffer));
        packet.setRotation(VarInts.readUnsignedInt(buffer));
        packet.setIgnoringEntities(buffer.readBoolean());
        packet.setIgnoringStructureBlocks(buffer.readBoolean());
        packet.setBbMin(BedrockUtils.readVector3i(buffer));
        packet.setBbMax(BedrockUtils.readVector3i(buffer));
        // Structure Settings end
        // Structure Editor Data end
        packet.setBoundingBoxVisible(buffer.readBoolean());
        packet.setPowered(buffer.readBoolean());
    }
}
