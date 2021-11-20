package com.nukkitx.protocol.bedrock.v475.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v465.serializer.StartGameSerializer_v465;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v475 extends StartGameSerializer_v465 {

    public static final StartGameSerializer_v475 INSTANCE = new StartGameSerializer_v475();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeLongLE(packet.getBlockRegistryChecksum());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet, session);

        packet.setBlockRegistryChecksum(buffer.readLongLE());
    }
}
