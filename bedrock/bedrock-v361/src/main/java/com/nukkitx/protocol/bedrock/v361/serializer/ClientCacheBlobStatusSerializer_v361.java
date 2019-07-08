package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ClientCacheBlobStatusPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.list.TLongList;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientCacheBlobStatusSerializer_v361 implements PacketSerializer<ClientCacheBlobStatusPacket> {
    public static final ClientCacheBlobStatusSerializer_v361 INSTANCE = new ClientCacheBlobStatusSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, ClientCacheBlobStatusPacket packet) {
        TLongList acks = packet.getAcks();
        TLongList nacks = packet.getNaks();
        VarInts.writeUnsignedInt(buffer, acks.size());
        VarInts.writeUnsignedInt(buffer, nacks.size());

        acks.forEach(blobId -> {
            buffer.writeLongLE(blobId);
            return true;
        });
        nacks.forEach(blobId -> {
            buffer.writeLongLE(blobId);
            return true;
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientCacheBlobStatusPacket packet) {
        int acksLength = VarInts.readUnsignedInt(buffer);
        int naksLength = VarInts.readUnsignedInt(buffer);

        TLongList acks = packet.getAcks();
        for (int i = 0; i < acksLength; i++) {
            acks.add(buffer.readLongLE());
        }

        TLongList naks = packet.getNaks();
        for (int i = 0; i < naksLength; i++) {
            naks.add(buffer.readLongLE());
        }
    }
}
