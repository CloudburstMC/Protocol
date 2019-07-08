package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackDataInfoSerializer_v361 implements PacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v361 INSTANCE = new ResourcePackDataInfoSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        BedrockUtils.writeString(buffer, packet.getPackId().toString());
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        byte[] hash = packet.getHash();
        VarInts.writeUnsignedInt(buffer, hash.length);
        buffer.writeBytes(hash);
        buffer.writeShortLE(packet.getUnknown0());
        buffer.writeBoolean(packet.isUnknown1());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        packet.setPackId(UUID.fromString(BedrockUtils.readString(buffer)));
        packet.setMaxChunkSize(buffer.readUnsignedIntLE());
        packet.setChunkCount(buffer.readUnsignedIntLE());
        packet.setCompressedPackSize(buffer.readLongLE());
        byte[] hash = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(hash);
        packet.setHash(hash);
        packet.setUnknown0(buffer.readUnsignedShortLE());
        packet.setUnknown1(buffer.readBoolean());
    }
}
