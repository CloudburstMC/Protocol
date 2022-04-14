package org.cloudburstmc.protocol.bedrock.codec.v503.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.TickingAreasLoadStatusPacket;

public class TickingAreasLoadStatusSerializer_v503 implements BedrockPacketSerializer<TickingAreasLoadStatusPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TickingAreasLoadStatusPacket packet) {
        buffer.writeBoolean(packet.isWaitingForPreload());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TickingAreasLoadStatusPacket packet) {
        packet.setWaitingForPreload(buffer.readBoolean());
    }
}
