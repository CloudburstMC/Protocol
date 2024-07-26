package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v534.serializer.EditorNetworkSerializer_v534;
import org.cloudburstmc.protocol.bedrock.packet.EditorNetworkPacket;

public class EditorNetworkSerializer_v712 extends EditorNetworkSerializer_v534 {
    public static final EditorNetworkSerializer_v712 INSTANCE = new EditorNetworkSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        buffer.writeBoolean(packet.isRouteToManager());
        super.serialize(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        packet.setRouteToManager(buffer.readBoolean());
        super.deserialize(buffer, helper, packet);
    }
}