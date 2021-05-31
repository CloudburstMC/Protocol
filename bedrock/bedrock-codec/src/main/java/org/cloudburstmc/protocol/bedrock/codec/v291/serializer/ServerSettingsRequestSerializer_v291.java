package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerSettingsRequestPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerSettingsRequestSerializer_v291 implements BedrockPacketSerializer<ServerSettingsRequestPacket> {
    public static final ServerSettingsRequestSerializer_v291 INSTANCE = new ServerSettingsRequestSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerSettingsRequestPacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerSettingsRequestPacket packet) {
        // No payload
    }
}
