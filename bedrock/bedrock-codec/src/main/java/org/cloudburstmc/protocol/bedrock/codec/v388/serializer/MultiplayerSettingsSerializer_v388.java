package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.MultiplayerMode;
import org.cloudburstmc.protocol.bedrock.packet.MultiplayerSettingsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiplayerSettingsSerializer_v388 implements BedrockPacketSerializer<MultiplayerSettingsPacket> {

    public static final MultiplayerSettingsSerializer_v388 INSTANCE = new MultiplayerSettingsSerializer_v388();

    private static final MultiplayerMode[] VALUES = MultiplayerMode.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MultiplayerSettingsPacket packet) {
        VarInts.writeInt(buffer, packet.getMode().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MultiplayerSettingsPacket packet) {
        packet.setMode(VALUES[VarInts.readInt(buffer)]);
    }
}
