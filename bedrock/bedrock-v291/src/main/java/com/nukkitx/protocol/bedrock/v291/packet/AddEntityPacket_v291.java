package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class AddEntityPacket_v291 extends AddEntityPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        VarInts.writeUnsignedInt(buffer, entityType);
        BedrockUtils.writeVector3f(buffer, position);
        BedrockUtils.writeVector3f(buffer, motion);
        BedrockUtils.writeVector3f(buffer, rotation);
        BedrockUtils.writeArray(buffer, attributes, BedrockUtils::writeEntityAttribute);
        BedrockUtils.writeMetadata(buffer, metadata);
        BedrockUtils.writeArray(buffer, entityLinks, BedrockUtils::writeEntityLink);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        entityType = VarInts.readUnsignedInt(buffer);
        position = BedrockUtils.readVector3f(buffer);
        motion = BedrockUtils.readVector3f(buffer);
        rotation = BedrockUtils.readVector3f(buffer);
        BedrockUtils.readArray(buffer, attributes, BedrockUtils::readEntityAttribute);
        BedrockUtils.readMetadata(buffer, metadata);
        BedrockUtils.readArray(buffer, entityLinks, BedrockUtils::readEntityLink);
    }
}
