package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v291.serializer.StartGameSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v313 extends StartGameSerializer_v291 {
    public static final StartGameSerializer_v313 INSTANCE = new StartGameSerializer_v313();

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);
        buffer.writeBoolean(packet.isFromWorldTemplate());
        buffer.writeBoolean(packet.isFromLockedWorldTemplate());
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);

        packet.setFromWorldTemplate(buffer.readBoolean());
        packet.setFromLockedWorldTemplate(buffer.readBoolean());
    }
}
