package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.EducationSettingsPacket;
import com.nukkitx.protocol.bedrock.v388.serializer.EducationSettingsSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationSettingsSerializer_v407 extends EducationSettingsSerializer_v388 {

    public static final EducationSettingsSerializer_v407 INSTANCE = new EducationSettingsSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
        helper.writeString(buffer, packet.getCodeBuilderUri());
        helper.writeString(buffer, packet.getCodeBuilderTitle());
        buffer.writeBoolean(packet.isCanResizeCodeBuilder());
        helper.writeOptional(buffer, Optional::isPresent, packet.getOverrideUri(),
                (byteBuf, optional) -> helper.writeString(byteBuf, optional.get()));
        buffer.writeBoolean(packet.isQuizAttached());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EducationSettingsPacket packet) {
        packet.setCodeBuilderUri(helper.readString(buffer));
        packet.setCodeBuilderTitle(helper.readString(buffer));
        packet.setCanResizeCodeBuilder(buffer.readBoolean());
        packet.setOverrideUri(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(helper.readString(byteBuf))));
        packet.setQuizAttached(buffer.readBoolean());
    }
}
