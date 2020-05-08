package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientCacheMissResponseSerializer_v363 implements PacketSerializer<ClientCacheMissResponsePacket> {
    public static final ClientCacheMissResponseSerializer_v363 INSTANCE = new ClientCacheMissResponseSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<byte[]> blobs = packet.getBlobs();
        VarInts.writeUnsignedInt(buffer, blobs.size());

        blobs.forEach((id, blob) -> {
            buffer.writeLongLE(id);
            BedrockUtils.writeByteArray(buffer, blob);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<byte[]> blobs = packet.getBlobs();

        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            long id = buffer.readLongLE();
            byte[] blob = BedrockUtils.readByteArray(buffer);
            blobs.put(id, blob);
        }
    }
}
