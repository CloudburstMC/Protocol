package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v503.serializer.DimensionDataSerializer_v503;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.bedrock.packet.DimensionDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class DimensionDataSerializer_v975 extends DimensionDataSerializer_v503 {
    public static final DimensionDataSerializer_v975 INSTANCE = new DimensionDataSerializer_v975();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DimensionDataPacket packet) {
        helper.writeArray(buffer, packet.getDefinitions(), this::writeDefinition);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DimensionDataPacket packet) {
        helper.readArray(buffer, packet.getDefinitions(), this::readDefinition);
    }

    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        super.writeDefinition(buffer, helper, definition);
        VarInts.writeInt(buffer, definition.getDimensionType());
    }

    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        int dimensionType = VarInts.readInt(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType, dimensionType);
    }
}