package org.cloudburstmc.protocol.bedrock.codec.v503.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.bedrock.packet.DimensionDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class DimensionDataSerializer_v503 implements BedrockPacketSerializer<DimensionDataPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DimensionDataPacket packet) {
        helper.writeArray(buffer, packet.getDefinitions(), this::writeDefinition);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DimensionDataPacket packet) {
        helper.readArray(buffer, packet.getDefinitions(), this::readDefinition);
    }

    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        helper.writeString(buffer, definition.getId());
        VarInts.writeInt(buffer, definition.getMaximumHeight());
        VarInts.writeInt(buffer, definition.getMinimumHeight());
        VarInts.writeInt(buffer, definition.getGeneratorType());
    }

    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType);
    }
}
