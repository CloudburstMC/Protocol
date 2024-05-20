package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v671.serializer.StartGameSerializer_v671;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

public class StartGameSerializer_v685 extends StartGameSerializer_v671 {
    public static final StartGameSerializer_v685 INSTANCE = new StartGameSerializer_v685();

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);
        helper.writeString(buffer, packet.getServerId());
        helper.writeString(buffer, packet.getWorldId());
        helper.writeString(buffer, packet.getScenarioId());
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);
        packet.setServerId(helper.readString(buffer));
        packet.setWorldId(helper.readString(buffer));
        packet.setScenarioId(helper.readString(buffer));
    }
}