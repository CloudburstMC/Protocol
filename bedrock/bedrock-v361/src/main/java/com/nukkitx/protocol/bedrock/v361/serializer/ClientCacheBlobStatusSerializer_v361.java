package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ClientCacheBlobStatusPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.LongConsumer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCacheBlobStatusSerializer_v361 implements BedrockPacketSerializer<ClientCacheBlobStatusPacket> {
    public static final ClientCacheBlobStatusSerializer_v361 INSTANCE = new ClientCacheBlobStatusSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheBlobStatusPacket packet) {
        LongList acks = packet.getAcks();
        LongList nacks = packet.getNaks();
        VarInts.writeUnsignedInt(buffer, acks.size());
        VarInts.writeUnsignedInt(buffer, nacks.size());

        acks.forEach((LongConsumer) buffer::writeLongLE);
        nacks.forEach((LongConsumer) buffer::writeLongLE);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCacheBlobStatusPacket packet) {
        int acksLength = VarInts.readUnsignedInt(buffer);
        int naksLength = VarInts.readUnsignedInt(buffer);

        LongList acks = packet.getAcks();
        for (int i = 0; i < acksLength; i++) {
            acks.add(buffer.readLongLE());
        }

        LongList naks = packet.getNaks();
        for (int i = 0; i < naksLength; i++) {
            naks.add(buffer.readLongLE());
        }
    }
}
