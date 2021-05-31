package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerFogPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerFogSerializer_v419 implements BedrockPacketSerializer<PlayerFogPacket> {

    public static final PlayerFogSerializer_v419 INSTANCE = new PlayerFogSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerFogPacket packet) {
        helper.writeArray(buffer, packet.getFogStack(), (buf, hlp, fogEffect) -> hlp.writeString(buf, fogEffect));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerFogPacket packet) {
        helper.readArray(buffer, packet.getFogStack(), (buf, hlp) -> hlp.readString(buf));
    }
}
