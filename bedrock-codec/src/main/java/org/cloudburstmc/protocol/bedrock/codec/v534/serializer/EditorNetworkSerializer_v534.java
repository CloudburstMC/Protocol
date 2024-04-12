package org.cloudburstmc.protocol.bedrock.codec.v534.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.EditorNetworkPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditorNetworkSerializer_v534 implements BedrockPacketSerializer<EditorNetworkPacket> {
    public static final EditorNetworkSerializer_v534 INSTANCE = new EditorNetworkSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        helper.writeTag(buffer, packet.getPayload());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        packet.setPayload(helper.readTag(buffer, Object.class));
    }
}
