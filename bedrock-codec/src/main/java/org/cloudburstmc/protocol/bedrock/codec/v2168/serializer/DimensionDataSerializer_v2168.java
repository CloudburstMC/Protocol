package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.DimensionDataSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.UUID;

public class DimensionDataSerializer_v2168 extends DimensionDataSerializer_v975 {

    public static final DimensionDataSerializer_v2168 INSTANCE = new DimensionDataSerializer_v2168();

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        super.writeDefinition(buffer, helper, definition);
        helper.writeUuid(buffer, definition.getPackId());
    }

    @Override
    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        int dimensionType = VarInts.readInt(buffer);
        UUID packId = helper.readUuid(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType, dimensionType, packId, null, 0, false);
    }
}