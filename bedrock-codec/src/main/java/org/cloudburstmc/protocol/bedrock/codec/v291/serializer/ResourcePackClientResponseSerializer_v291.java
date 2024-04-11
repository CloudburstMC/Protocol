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
        helper.writeArray(buffer, packet.getPackIds(), ByteBuf::writeShortLE, helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackClientResponsePacket packet) {
        Status status = Status.values()[buffer.readUnsignedByte()];
        packet.setStatus(status);
        helper.readArray(buffer, packet.getPackIds(), ByteBuf::readUnsignedShortLE, helper::readString);
    }
}
