package org.cloudburstmc.protocol.bedrock.codec.v589.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v582.serializer.StartGameSerializer_v582;
import org.cloudburstmc.protocol.bedrock.data.NetworkPermissions;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

public class StartGameSerializer_v589 extends StartGameSerializer_v582 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);
        this.writeNetworkPermissions(buffer, helper, packet.getNetworkPermissions());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setNetworkPermissions(this.readNetworkPermissions(buffer, helper));
    }

    protected NetworkPermissions readNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper) {
        boolean serverAuthSound = buffer.readBoolean();
        return new NetworkPermissions(serverAuthSound);
    }

    protected void writeNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper, NetworkPermissions permissions) {
        buffer.writeBoolean(permissions.isServerAuthSounds());
    }
}
