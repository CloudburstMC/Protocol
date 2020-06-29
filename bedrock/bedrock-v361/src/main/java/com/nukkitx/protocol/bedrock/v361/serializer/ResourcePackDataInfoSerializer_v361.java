package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket.Type.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackDataInfoSerializer_v361 implements BedrockPacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v361 INSTANCE = new ResourcePackDataInfoSerializer_v361();

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
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
        String packInfo = packet.getPackId().toString() + (packet.getPackVersion() == null ? "" : '_' + packet.getPackVersion());
        helper.writeString(buffer, packInfo);
        buffer.writeIntLE((int) packet.getMaxChunkSize());
        buffer.writeIntLE((int) packet.getChunkCount());
        buffer.writeLongLE(packet.getCompressedPackSize());
        byte[] hash = packet.getHash();
        VarInts.writeUnsignedInt(buffer, hash.length);
        buffer.writeBytes(hash);
        buffer.writeBoolean(packet.isPremium());
        buffer.writeByte(TYPES.get(packet.getType()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackDataInfoPacket packet) {
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
        packet.setType(TYPES.get(buffer.readUnsignedByte()));
    }
}
