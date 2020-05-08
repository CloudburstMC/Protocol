package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.SettingsCommandPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SettingsCommandSerializer_v363 implements PacketSerializer<SettingsCommandPacket> {

    public static final SettingsCommandSerializer_v363 INSTANCE = new SettingsCommandSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, SettingsCommandPacket packet) {
        BedrockUtils.writeString(buffer, packet.getCommand());
        buffer.writeBoolean(packet.isSuppressingOutput());
    }

    @Override
    public void deserialize(ByteBuf buffer, SettingsCommandPacket packet) {
        packet.setCommand(BedrockUtils.readString(buffer));
        packet.setSuppressingOutput(buffer.readBoolean());
    }
}
