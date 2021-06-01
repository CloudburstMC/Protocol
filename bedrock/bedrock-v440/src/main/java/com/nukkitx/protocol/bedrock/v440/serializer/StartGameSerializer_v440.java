package com.nukkitx.protocol.bedrock.v440.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v428.serializer.StartGameSerializer_v428;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v440 extends StartGameSerializer_v428 {

    public static final StartGameSerializer_v440 INSTANCE = new StartGameSerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeString(buffer, packet.getServerEngine());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet, session);
        packet.setServerEngine(helper.readString(buffer));
    }
}
