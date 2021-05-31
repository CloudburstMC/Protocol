package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SettingsCommandPacket;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SettingsCommandSerializer_v388 implements BedrockPacketSerializer<SettingsCommandPacket> {

    public static final SettingsCommandSerializer_v388 INSTANCE = new SettingsCommandSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SettingsCommandPacket packet) {
        helper.writeString(buffer, packet.getCommand());
        buffer.writeBoolean(packet.isSuppressingOutput());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SettingsCommandPacket packet) {
        packet.setCommand(helper.readString(buffer));
        packet.setSuppressingOutput(buffer.readBoolean());
    }
}
