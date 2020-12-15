package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.MultiplayerMode;
import com.nukkitx.protocol.bedrock.packet.MultiplayerSettingsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiplayerSettingsSerializer_v388 implements BedrockPacketSerializer<MultiplayerSettingsPacket> {

    public static final MultiplayerSettingsSerializer_v388 INSTANCE = new MultiplayerSettingsSerializer_v388();

    private static final MultiplayerMode[] VALUES = MultiplayerMode.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MultiplayerSettingsPacket packet) {
        VarInts.writeInt(buffer, packet.getMode().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MultiplayerSettingsPacket packet) {
        packet.setMode(VALUES[VarInts.readInt(buffer)]);
    }
}
