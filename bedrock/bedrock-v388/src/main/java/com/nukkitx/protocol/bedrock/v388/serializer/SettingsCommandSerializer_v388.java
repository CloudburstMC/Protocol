package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SettingsCommandPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SettingsCommandSerializer_v388 implements BedrockPacketSerializer<SettingsCommandPacket> {

    public static final SettingsCommandSerializer_v388 INSTANCE = new SettingsCommandSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SettingsCommandPacket packet) {
        helper.writeString(buffer, packet.getCommand());
        buffer.writeBoolean(packet.isSuppressingOutput());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SettingsCommandPacket packet) {
        packet.setCommand(helper.readString(buffer));
        packet.setSuppressingOutput(buffer.readBoolean());
    }
}
