package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackChunkDataSerializer_v388 implements PacketSerializer<ResourcePackChunkDataPacket> {
    public static final ResourcePackChunkDataSerializer_v388 INSTANCE = new ResourcePackChunkDataSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, ResourcePackChunkDataPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        BedrockUtils.writeString(buffer, packInfo);
        buffer.writeIntLE(packet.getChunkIndex());
        buffer.writeLongLE(packet.getProgress());
        BedrockUtils.writeByteArray(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackChunkDataPacket packet) {
        String[] packInfo = BedrockUtils.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setChunkIndex(buffer.readIntLE());
        packet.setProgress(buffer.readLongLE());
        packet.setData(BedrockUtils.readByteArray(buffer));
    }
}
