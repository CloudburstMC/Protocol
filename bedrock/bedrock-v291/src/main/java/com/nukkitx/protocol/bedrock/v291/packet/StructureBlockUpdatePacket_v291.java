package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class StructureBlockUpdatePacket_v291 extends StructureBlockUpdatePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        VarInts.writeUnsignedInt(buffer, structureType.ordinal());
        // Structure Editor Data start
        BedrockUtils.writeString(buffer, customName);
        BedrockUtils.writeString(buffer, metadata);
        BedrockUtils.writeBlockPosition(buffer, structureOffset);
        BedrockUtils.writeBlockPosition(buffer, structureSize);
        buffer.writeBoolean(includingEntities);
        buffer.writeBoolean(ignoringBlocks);
        buffer.writeBoolean(includingPlayers);
        buffer.writeBoolean(showingAir);
        // Structure Settings start
        buffer.writeFloatLE(integrity);
        VarInts.writeUnsignedInt(buffer, seed);
        VarInts.writeUnsignedInt(buffer, mirror);
        VarInts.writeUnsignedInt(buffer, rotation);
        buffer.writeBoolean(ignoringEntities);
        buffer.writeBoolean(ignoringStructureBlocks);
        BedrockUtils.writeVector3i(buffer, bbMin);
        BedrockUtils.writeVector3i(buffer, bbMax);
        // Structure Settings end
        // Structure Editor Data end
        buffer.writeBoolean(boundingBoxVisible);
        buffer.writeBoolean(powered);
    }

    @Override
    public void decode(ByteBuf buffer) {
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        structureType = Type.values()[VarInts.readUnsignedInt(buffer)];
        // Structure Editor Data start
        customName = BedrockUtils.readString(buffer);
        metadata = BedrockUtils.readString(buffer);
        structureOffset = BedrockUtils.readBlockPosition(buffer);
        structureSize = BedrockUtils.readBlockPosition(buffer);
        includingEntities = buffer.readBoolean();
        ignoringBlocks = buffer.readBoolean();
        includingPlayers = buffer.readBoolean();
        showingAir = buffer.readBoolean();
        // Structure Settings start
        integrity = buffer.readFloatLE();
        seed = VarInts.readUnsignedInt(buffer);
        mirror = VarInts.readUnsignedInt(buffer);
        rotation = VarInts.readUnsignedInt(buffer);
        ignoringEntities = buffer.readBoolean();
        ignoringStructureBlocks = buffer.readBoolean();
        bbMin = BedrockUtils.readVector3i(buffer);
        bbMax = BedrockUtils.readVector3i(buffer);
        // Structure Settings end
        // Structure Editor Data end
        boundingBoxVisible = buffer.readBoolean();
        powered = buffer.readBoolean();
    }
}
