package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.EduSharedUriResource;
import org.cloudburstmc.protocol.bedrock.packet.EduUriResourcePacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EduUriResourceSerializer_v465 implements BedrockPacketSerializer<EduUriResourcePacket> {
    public static final EduUriResourceSerializer_v465 INSTANCE = new EduUriResourceSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EduUriResourcePacket packet) {
        helper.writeString(buffer, packet.getEduSharedUriResource().getButtonName());
        helper.writeString(buffer, packet.getEduSharedUriResource().getLinkUri());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EduUriResourcePacket packet) {
        packet.setEduSharedUriResource(new EduSharedUriResource(helper.readString(buffer), helper.readString(buffer)));
    }
}
