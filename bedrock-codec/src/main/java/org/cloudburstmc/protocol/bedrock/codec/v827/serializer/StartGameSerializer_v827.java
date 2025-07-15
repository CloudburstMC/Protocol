package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.StartGameSerializer_v818;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

public class StartGameSerializer_v827 extends StartGameSerializer_v818 {

    public static final StartGameSerializer_v827 INSTANCE = new StartGameSerializer_v827();

    @Override
    protected void readBeforeNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        packet.setTickDeathSystemsEnabled(buffer.readBoolean());
        packet.setNetworkPermissions(this.readNetworkPermissions(buffer, helper));
    }

    @Override
    protected void writeBeforeNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        buffer.writeBoolean(packet.isTickDeathSystemsEnabled());
        this.writeNetworkPermissions(buffer, helper, packet.getNetworkPermissions());
    }
}
