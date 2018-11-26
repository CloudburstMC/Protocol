package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class AddItemEntityPacket_v291 extends AddItemEntityPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeItemInstance(buffer, itemInstance);
        BedrockUtils.writeVector3f(buffer, position);
        BedrockUtils.writeVector3f(buffer, motion);
        BedrockUtils.writeMetadata(buffer, metadata);
        buffer.writeBoolean(fromFishing);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        itemInstance = BedrockUtils.readItemInstance(buffer);
        position = BedrockUtils.readVector3f(buffer);
        motion = BedrockUtils.readVector3f(buffer);
        BedrockUtils.readMetadata(buffer, metadata);
        fromFishing = buffer.readBoolean();
    }
}
