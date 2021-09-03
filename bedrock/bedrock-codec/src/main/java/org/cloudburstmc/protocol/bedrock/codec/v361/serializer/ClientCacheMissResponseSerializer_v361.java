package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.nio.ByteBuffer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCacheMissResponseSerializer_v361 implements BedrockPacketSerializer<ClientCacheMissResponsePacket> {
    public static final ClientCacheMissResponseSerializer_v361 INSTANCE = new ClientCacheMissResponseSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<ByteBuffer> blobs = packet.getBlobs();

        VarInts.writeUnsignedInt(buffer, blobs.size());
        for (Long2ObjectMap.Entry<ByteBuffer> entry : blobs.long2ObjectEntrySet()) {
            buffer.writeLongLE(entry.getLongKey());
            helper.writeByteBuffer(buffer, entry.getValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientCacheMissResponsePacket packet) {
        Long2ObjectMap<ByteBuffer> blobs = packet.getBlobs();

        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            long id = buffer.readLongLE();
            ByteBuffer blob = helper.readByteBuffer(buffer);
            blobs.put(id, blob);
        }
    }
}
