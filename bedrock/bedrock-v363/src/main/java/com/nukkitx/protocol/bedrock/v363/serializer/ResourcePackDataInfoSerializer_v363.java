package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket.Type.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackDataInfoSerializer_v363 implements PacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v363 INSTANCE = new ResourcePackDataInfoSerializer_v363();

    public static final Int2ObjectBiMap<ResourcePackDataInfoPacket.Type> TYPES = new Int2ObjectBiMap<>(INVALID);

    static {
        TYPES.put(0, INVALID);
        TYPES.put(1, RESOURCE);
        TYPES.put(2, BEHAVIOR);
        TYPES.put(3, WORLD_TEMPLATE);
        TYPES.put(4, ADDON);
        TYPES.put(5, SKINS);
        TYPES.put(6, CACHED);
        TYPES.put(7, COPY_PROTECTED);
    }

    @Override
    public void serialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        BedrockUtils.writeString(buffer, packInfo);
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        BedrockUtils.writeByteArray(buffer, packet.getHash());
        buffer.writeBoolean(packet.isPremium());
        buffer.writeByte(TYPES.get(packet.getType()));
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackDataInfoPacket packet) {
        String[] packInfo = BedrockUtils.readString(buffer).split("_");
        packet.setPackId(UUID.fromString(packInfo[0]));
        if (packInfo.length > 1) {
            packet.setPackVersion(packInfo[1]);
        }
        packet.setMaxChunkSize(buffer.readUnsignedIntLE());
        packet.setChunkCount(buffer.readUnsignedIntLE());
        packet.setCompressedPackSize(buffer.readLongLE());
        packet.setHash(BedrockUtils.readByteArray(buffer));
        packet.setPremium(buffer.readBoolean());
        packet.setType(TYPES.get(buffer.readUnsignedByte()));
    }
}
