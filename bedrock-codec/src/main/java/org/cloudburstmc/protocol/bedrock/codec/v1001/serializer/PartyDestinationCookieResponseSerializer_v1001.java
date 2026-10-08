package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PartyDestinationCookieResponsePacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyDestinationCookieResponseSerializer_v1001 implements BedrockPacketSerializer<PartyDestinationCookieResponsePacket> {

    public static final PartyDestinationCookieResponseSerializer_v1001 INSTANCE = new PartyDestinationCookieResponseSerializer_v1001();

    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PartyDestinationCookieResponsePacket packet) {
        helper.writeString(buffer, packet.getCookie());
        buffer.writeBoolean(packet.isAccepted());
    }

    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PartyDestinationCookieResponsePacket packet) {
        packet.setCookie(helper.readStringMaxLen(buffer, 2048));
        packet.setAccepted(buffer.readBoolean());
    }
}
