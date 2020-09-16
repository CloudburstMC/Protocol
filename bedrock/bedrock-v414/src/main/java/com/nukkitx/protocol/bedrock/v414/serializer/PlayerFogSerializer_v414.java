package com.nukkitx.protocol.bedrock.v414.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayerFogPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerFogSerializer_v414 implements BedrockPacketSerializer<PlayerFogPacket> {

    public static final PlayerFogSerializer_v414 INSTANCE = new PlayerFogSerializer_v414();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerFogPacket packet) {
        helper.writeArray(buffer, packet.getFogStack(), (buf, hlp, fogEffect) -> hlp.writeString(buf, fogEffect));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerFogPacket packet) {
        helper.readArray(buffer, packet.getFogStack(), (buf, hlp) -> hlp.readString(buf));
    }
}
