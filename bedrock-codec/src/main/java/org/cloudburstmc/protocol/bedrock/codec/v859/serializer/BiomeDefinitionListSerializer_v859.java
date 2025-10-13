package org.cloudburstmc.protocol.bedrock.codec.v859.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v844.serializer.BiomeDefinitionListSerializer_v844;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;

import java.util.ArrayList;
import java.util.List;

public class BiomeDefinitionListSerializer_v859 extends BiomeDefinitionListSerializer_v844 {

    public static final BiomeDefinitionListSerializer_v859 INSTANCE = new BiomeDefinitionListSerializer_v859();

    @Override
    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen,
                                           SequencedHashSet<String> strings) {
        super.writeDefinitionChunkGen(buffer, helper, definitionChunkGen, strings);
        helper.writeOptionalNull(buffer, definitionChunkGen.getBiomeReplacementData(), this::writeBiomeReplacementData);
    }

    @Override
    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
        boolean hasDefaultOverworldSurface = buffer.readBoolean();
        boolean hasSwampSurface = buffer.readBoolean();
        boolean hasFrozenOceanSurface = buffer.readBoolean();
        boolean hasTheEndSurface = buffer.readBoolean();
        BiomeMesaSurfaceData mesaSurface = helper.readOptional(buffer, null, this::readMesaSurface);
        BiomeCappedSurfaceData cappedSurface = helper.readOptional(buffer, null, this::readCappedSurface);
        BiomeOverworldGenRulesData overworldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readOverworldGenRules(buf, aHelper, strings));
        BiomeMultinoiseGenRulesData multinoiseGenRules = helper.readOptional(buffer, null, this::readMultinoiseGenRules);
        BiomeLegacyWorldGenRulesData legacyWorldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readLegacyWorldGenRules(buf, aHelper, strings));
        BiomeReplacementData replacementData = helper.readOptional(buffer, null, this::readBiomeReplacementData);

        return new BiomeDefinitionChunkGenData(climate, consolidatedFeatures,
                mountainParams, surfaceMaterialAdjustment,
                surfaceMaterial, hasDefaultOverworldSurface, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface,
                mesaSurface, cappedSurface,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules, replacementData);
    }

    protected void writeBiomeReplacementData(ByteBuf buffer, BedrockCodecHelper helper, BiomeReplacementData replacementData) {
        buffer.writeShortLE(replacementData.getBiome());
        buffer.writeShortLE(replacementData.getDimension());
        helper.writeArray(buffer, replacementData.getTargetBiomes(), (buf, value) -> buf.writeShortLE(value));
        buffer.writeFloatLE(replacementData.getAmount());
        buffer.writeFloatLE(replacementData.getNoiseFrequencyScale());
        buffer.writeIntLE(replacementData.getReplacementIndex());
    }

    protected BiomeReplacementData readBiomeReplacementData(ByteBuf buffer, BedrockCodecHelper helper) {
        int biome = buffer.readShortLE();
        int dimension = buffer.readShortLE();
        List<Short> targetBiomes = new ArrayList<>();
        helper.readArray(buffer, targetBiomes, (buf, aHelper) -> buf.readShortLE());
        float amount = buffer.readFloatLE();
        float noiseFrequencyScale = buffer.readFloatLE();
        int replacementIndex = buffer.readIntLE();

        return new BiomeReplacementData(biome, dimension, targetBiomes, amount, noiseFrequencyScale, replacementIndex);
    }
}
