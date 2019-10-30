package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.map.TLongObjectMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientCacheMissResponseSerializer_v388 implements PacketSerializer<ClientCacheMissResponsePacket> {
    public static final ClientCacheMissResponseSerializer_v388 INSTANCE = new ClientCacheMissResponseSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, ClientCacheMissResponsePacket packet) {
        TLongObjectMap<byte[]> blobs = packet.getBlobs();
        VarInts.writeUnsignedInt(buffer, blobs.size());

        blobs.forEachEntry((id, blob) -> {
            buffer.writeLongLE(id);
            BedrockUtils.writeByteArray(buffer, blob);
            return true;
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientCacheMissResponsePacket packet) {
        TLongObjectMap<byte[]> blobs = packet.getBlobs();

        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            long id = buffer.readLongLE();
            byte[] blob = BedrockUtils.readByteArray(buffer);
            blobs.put(id, blob);
        }
    }
}
