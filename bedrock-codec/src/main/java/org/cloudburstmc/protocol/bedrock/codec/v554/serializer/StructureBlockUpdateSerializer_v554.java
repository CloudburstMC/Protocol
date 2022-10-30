package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.StructureBlockUpdateSerializer_v388;
import org.cloudburstmc.protocol.bedrock.packet.StructureBlockUpdatePacket;

public class StructureBlockUpdateSerializer_v554 extends StructureBlockUpdateSerializer_v388 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StructureBlockUpdatePacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isWaterlogged());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StructureBlockUpdatePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setWaterlogged(buffer.readBoolean());
    }
}