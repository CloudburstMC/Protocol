package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackClientResponsePacket;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.cloudburstmc.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackClientResponseSerializer_v291 implements BedrockPacketSerializer<ResourcePackClientResponsePacket> {
    public static final ResourcePackClientResponseSerializer_v291 INSTANCE = new ResourcePackClientResponseSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackClientResponsePacket packet) {
        buffer.writeByte(packet.getStatus().ordinal());

        writeArrayShortLE(buffer, packet.getPackIds(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackClientResponsePacket packet) {
        Status status = Status.values()[buffer.readUnsignedByte()];
        packet.setStatus(status);

        readArrayShortLE(buffer, packet.getPackIds(), helper::readString);
    }

    protected <T> void readArrayShortLE(ByteBuf buffer, Collection<T> collection, Function<ByteBuf, T> function) {
        int length = buffer.readUnsignedShortLE();
        for (int i = 0; i < length; i++) {
            collection.add(function.apply(buffer));
        }
    }

    protected <T> void writeArrayShortLE(ByteBuf buffer, Collection<T> collection, BiConsumer<ByteBuf, T> consumer) {
        buffer.writeShortLE(collection.size());
        for (T t : collection) {
            consumer.accept(buffer, t);
        }
    }
}
