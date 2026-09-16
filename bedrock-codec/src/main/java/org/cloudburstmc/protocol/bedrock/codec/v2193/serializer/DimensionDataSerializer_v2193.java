package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.DimensionDataSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.UUID;

public class DimensionDataSerializer_v2193 extends DimensionDataSerializer_v2168 {

    public static final DimensionDataSerializer_v2193 INSTANCE = new DimensionDataSerializer_v2193();

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        super.writeDefinition(buffer, helper, definition);
        helper.writeString(buffer, definition.getDefaultBiome());
    }

    @Override
    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        int dimensionType = VarInts.readInt(buffer);
        UUID packId = helper.readUuid(buffer);
        String defaultBiome = helper.readString(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType, dimensionType, packId, defaultBiome);
    }
}
