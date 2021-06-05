package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RemoveEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveEntitySerializer_v291 implements BedrockPacketSerializer<RemoveEntityPacket> {
    public static final RemoveEntitySerializer_v291 INSTANCE = new RemoveEntitySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RemoveEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RemoveEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
    }
}
