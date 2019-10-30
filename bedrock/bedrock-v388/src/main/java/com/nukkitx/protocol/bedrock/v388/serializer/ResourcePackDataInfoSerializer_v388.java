package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackDataInfoSerializer_v388 implements PacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v388 INSTANCE = new ResourcePackDataInfoSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        BedrockUtils.writeString(buffer, packet.getPackId().toString());
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        byte[] hash = packet.getHash();
        VarInts.writeUnsignedInt(buffer, hash.length);
        buffer.writeBytes(hash);
        buffer.writeBoolean(packet.isPremium());
        buffer.writeByte(packet.getType().ordinal());
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
        packet.setPremium(buffer.readBoolean());
        packet.setType(ResourcePackDataInfoPacket.Type.values()[buffer.readUnsignedByte()]);
    }
}
