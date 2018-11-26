package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class AddPlayerPacket_v291 extends AddPlayerPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeUuid(buffer, uuid);
        BedrockUtils.writeString(buffer, username);
        VarInts.writeLong(buffer, uniqueEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeString(buffer, platformChatId);
        BedrockUtils.writeVector3f(buffer, position);
        BedrockUtils.writeVector3f(buffer, motion);
        BedrockUtils.writeVector3f(buffer, rotation);
        BedrockUtils.writeItemInstance(buffer, hand);
        BedrockUtils.writeMetadata(buffer, metadata);
        // Adventure Settings start
        VarInts.writeUnsignedInt(buffer, playerFlags);
        VarInts.writeUnsignedInt(buffer, commandPermission);
        VarInts.writeUnsignedInt(buffer, worldFlags);
        VarInts.writeUnsignedInt(buffer, playerPermission);
        VarInts.writeUnsignedInt(buffer, customFlags);
        buffer.writeLongLE(uniqueEntityId);
        // Adventure Settings end
        BedrockUtils.writeArray(buffer, entityLinks, BedrockUtils::writeEntityLink);
        BedrockUtils.writeString(buffer, deviceId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uuid = BedrockUtils.readUuid(buffer);
        username = BedrockUtils.readString(buffer);
        uniqueEntityId = VarInts.readLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        platformChatId = BedrockUtils.readString(buffer);
        position = BedrockUtils.readVector3f(buffer);
        motion = BedrockUtils.readVector3f(buffer);
        rotation = BedrockUtils.readVector3f(buffer);
        hand = BedrockUtils.readItemInstance(buffer);
        BedrockUtils.readMetadata(buffer, metadata);
        // Adventure Settings start
        playerFlags = VarInts.readUnsignedInt(buffer);
        commandPermission = VarInts.readUnsignedInt(buffer);
        worldFlags = VarInts.readUnsignedInt(buffer);
        playerPermission = VarInts.readUnsignedInt(buffer);
        customFlags = VarInts.readUnsignedInt(buffer);
        buffer.readLongLE(); // Ignore
        // Adventure settings end
        BedrockUtils.readArray(buffer, entityLinks, BedrockUtils::readEntityLink);
        deviceId = BedrockUtils.readString(buffer);
    }
}
