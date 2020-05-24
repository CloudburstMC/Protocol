package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackChunkRequestSerializer_v363 implements PacketSerializer<ResourcePackChunkRequestPacket> {
    public static final ResourcePackChunkRequestSerializer_v363 INSTANCE = new ResourcePackChunkRequestSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, ResourcePackChunkRequestPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        BedrockUtils.writeString(buffer, packInfo);
        buffer.writeIntLE(packet.getChunkIndex());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackChunkRequestPacket packet) {
        String[] packInfo = BedrockUtils.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setChunkIndex(buffer.readIntLE());
    }
}
