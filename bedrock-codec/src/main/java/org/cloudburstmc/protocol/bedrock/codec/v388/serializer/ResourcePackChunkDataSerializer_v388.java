package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackChunkDataPacket;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackChunkDataSerializer_v388 implements BedrockPacketSerializer<ResourcePackChunkDataPacket> {
    public static final ResourcePackChunkDataSerializer_v388 INSTANCE = new ResourcePackChunkDataSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackChunkDataPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        helper.writeString(buffer, packInfo);
        buffer.writeIntLE(packet.getChunkIndex());
        buffer.writeLongLE(packet.getProgress());
        helper.writeByteBuf(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackChunkDataPacket packet) {
        String[] packInfo = helper.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setChunkIndex(buffer.readIntLE());
        packet.setProgress(buffer.readLongLE());
        packet.setData(helper.readByteBuf(buffer));
    }
}
