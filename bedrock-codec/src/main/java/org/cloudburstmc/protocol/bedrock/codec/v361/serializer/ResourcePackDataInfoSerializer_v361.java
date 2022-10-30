package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.UUID;

@RequiredArgsConstructor
public class ResourcePackDataInfoSerializer_v361 implements BedrockPacketSerializer<ResourcePackDataInfoPacket> {
    private final TypeMap<ResourcePackType> typeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackDataInfoPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        helper.writeString(buffer, packInfo);
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        byte[] hash = packet.getHash();
        VarInts.writeUnsignedInt(buffer, hash.length);
        buffer.writeBytes(hash);
        buffer.writeBoolean(packet.isPremium());
        buffer.writeByte(typeMap.getId(packet.getType()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackDataInfoPacket packet) {
        String[] packInfo = helper.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setMaxChunkSize(buffer.readUnsignedIntLE());
        packet.setChunkCount(buffer.readUnsignedIntLE());
        packet.setCompressedPackSize(buffer.readLongLE());
        byte[] hash = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(hash);
        packet.setHash(hash);
        packet.setPremium(buffer.readBoolean());
        packet.setType(typeMap.getType(buffer.readUnsignedByte()));
    }
}
