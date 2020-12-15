package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCacheMissResponseSerializer_v361 implements BedrockPacketSerializer<ClientCacheMissResponsePacket> {
    public static final ClientCacheMissResponseSerializer_v361 INSTANCE = new ClientCacheMissResponseSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<byte[]> blobs = packet.getBlobs();

        VarInts.writeUnsignedInt(buffer, blobs.size());
        for (Long2ObjectMap.Entry<byte[]> entry : blobs.long2ObjectEntrySet()) {
            buffer.writeLongLE(entry.getLongKey());
            helper.writeByteArray(buffer, entry.getValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<byte[]> blobs = packet.getBlobs();

        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            long id = buffer.readLongLE();
            byte[] blob = helper.readByteArray(buffer);
            blobs.put(id, blob);
        }
    }
}
