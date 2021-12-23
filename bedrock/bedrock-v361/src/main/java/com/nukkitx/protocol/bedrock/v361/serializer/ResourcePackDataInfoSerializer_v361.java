package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackDataInfoSerializer_v361 implements BedrockPacketSerializer<ResourcePackDataInfoPacket> {
    public static final ResourcePackDataInfoSerializer_v361 INSTANCE = new ResourcePackDataInfoSerializer_v361();

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
        buffer.writeByte(helper.getResourcePackTypeId(packet.getType()));
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
        packet.setType(helper.getResourcePackType(buffer.readUnsignedByte()));
    }
}
