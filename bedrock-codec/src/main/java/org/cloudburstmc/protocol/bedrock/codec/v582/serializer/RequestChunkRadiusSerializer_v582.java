package org.cloudburstmc.protocol.bedrock.codec.v582.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.RequestChunkRadiusSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestChunkRadiusSerializer_v582 extends RequestChunkRadiusSerializer_v291 {
    public static final RequestChunkRadiusSerializer_v582 INSTANCE = new RequestChunkRadiusSerializer_v582();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RequestChunkRadiusPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeByte(packet.getMaxRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RequestChunkRadiusPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setMaxRadius(buffer.readUnsignedByte());
    }
}
