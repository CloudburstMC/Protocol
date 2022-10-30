package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestChunkRadiusSerializer_v291 implements BedrockPacketSerializer<RequestChunkRadiusPacket> {
    public static final RequestChunkRadiusSerializer_v291 INSTANCE = new RequestChunkRadiusSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RequestChunkRadiusPacket packet) {
        VarInts.writeInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RequestChunkRadiusPacket packet) {
        packet.setRadius(VarInts.readInt(buffer));
    }
}
