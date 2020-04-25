package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackDataInfoSerializer_v313 implements PacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v313 INSTANCE = new ResourcePackDataInfoSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        BedrockUtils.writeString(buffer, packInfo);
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        BedrockUtils.writeByteArray(buffer, packet.getHash());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        String[] packInfo = BedrockUtils.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setMaxChunkSize(buffer.readIntLE());
        packet.setChunkCount(buffer.readIntLE());
        packet.setCompressedPackSize(buffer.readLongLE());
        packet.setHash(BedrockUtils.readByteArray(buffer));
    }
}
