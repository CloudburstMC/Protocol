package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackChunkDataSerializer_v291 implements PacketSerializer<ResourcePackChunkDataPacket> {
    public static final ResourcePackChunkDataSerializer_v291 INSTANCE = new ResourcePackChunkDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, ResourcePackChunkDataPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        BedrockUtils.writeString(buffer, packInfo);
        buffer.writeIntLE(packet.getChunkIndex());
        buffer.writeLongLE(packet.getProgress());
        byte[] data = packet.getData();
        buffer.writeIntLE(data.length);
        buffer.writeBytes(data);
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
        byte[] data = new byte[buffer.readIntLE()];
        buffer.readBytes(data);
        packet.setData(data);
    }
}
