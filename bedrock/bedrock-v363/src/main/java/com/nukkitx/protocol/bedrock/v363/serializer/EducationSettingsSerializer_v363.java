package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.EducationSettingsPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EducationSettingsSerializer_v363 implements PacketSerializer<EducationSettingsPacket> {

    public static final EducationSettingsSerializer_v363 INSTANCE = new EducationSettingsSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, EducationSettingsPacket packet) {
        BedrockUtils.writeString(buffer, packet.getSettings());
        buffer.writeBoolean(packet.isUnknown0());
    }

    @Override
    public void deserialize(ByteBuf buffer, EducationSettingsPacket packet) {
        packet.setSettings(BedrockUtils.readString(buffer));
        packet.setUnknown0(buffer.readBoolean());
    }
}
