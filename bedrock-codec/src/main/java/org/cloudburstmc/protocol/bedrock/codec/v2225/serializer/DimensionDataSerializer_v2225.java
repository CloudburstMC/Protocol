package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.DimensionDataSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.UUID;

public class DimensionDataSerializer_v2225 extends DimensionDataSerializer_v2193 {

    public static final DimensionDataSerializer_v2225 INSTANCE = new DimensionDataSerializer_v2225();

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        super.writeDefinition(buffer, helper, definition);
        VarInts.writeInt(buffer, definition.getCloudHeight());
        buffer.writeBoolean(definition.isRenderClouds());
    }

    @Override
    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int maximumHeight = minimumHeight + VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        int dimensionType = VarInts.readInt(buffer);
        UUID packId = helper.readUuid(buffer);
        String defaultBiome = helper.readString(buffer);
        int cloudHeight = VarInts.readInt(buffer); //new
        boolean isRenderClouds = buffer.readBoolean(); //new
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType, dimensionType, packId, defaultBiome, cloudHeight, isRenderClouds);
    }
}
