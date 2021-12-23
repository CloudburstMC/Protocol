package com.nukkitx.protocol.bedrock.v465.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.EduSharedUriResource;
import com.nukkitx.protocol.bedrock.packet.EduUriResourcePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EduUriResourceSerializer_v465 implements BedrockPacketSerializer<EduUriResourcePacket> {

    public static final EduUriResourceSerializer_v465 INSTANCE = new EduUriResourceSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EduUriResourcePacket packet) {
        helper.writeString(buffer, packet.getEduSharedUriResource().getButtonName());
        helper.writeString(buffer, packet.getEduSharedUriResource().getLinkUri());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EduUriResourcePacket packet) {
        packet.setEduSharedUriResource(new EduSharedUriResource(helper.readString(buffer), helper.readString(buffer)));
    }
}
