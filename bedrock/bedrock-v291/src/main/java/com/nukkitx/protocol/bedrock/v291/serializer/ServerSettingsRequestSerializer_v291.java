package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerSettingsRequestSerializer_v291 implements BedrockPacketSerializer<ServerSettingsRequestPacket> {
    public static final ServerSettingsRequestSerializer_v291 INSTANCE = new ServerSettingsRequestSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsRequestPacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsRequestPacket packet) {
        // No payload
    }
}
