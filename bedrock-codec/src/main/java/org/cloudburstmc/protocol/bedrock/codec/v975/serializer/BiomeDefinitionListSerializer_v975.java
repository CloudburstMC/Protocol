package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.BiomeDefinitionListSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.VillageType;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;

import java.util.ArrayList;
import java.util.List;

public class BiomeDefinitionListSerializer_v975 extends BiomeDefinitionListSerializer_v924 {

    public static final BiomeDefinitionListSerializer_v975 INSTANCE = new BiomeDefinitionListSerializer_v975();

    @Override
    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen, SequencedHashSet<String> strings) {
        helper.writeOptionalNull(buffer, definitionChunkGen.getClimate(), this::writeClimate);

        helper.writeOptionalNull(buffer, definitionChunkGen.getConsolidatedFeatures(),
                (buf, aHelper, consolidatedFeatures) -> this.writeConsolidatedFeatures(buf, aHelper, consolidatedFeatures, strings));

        helper.writeOptionalNull(buffer, definitionChunkGen.getMountainParams(), this::writeMountainParamsData);

        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceMaterialAdjustment(),
                (buf, aHelper, surfaceMaterialAdjustment) -> this.writeSurfaceMaterialAdjustment(buf, aHelper, surfaceMaterialAdjustment, strings));

        helper.writeOptionalNull(buffer, definitionChunkGen.getOverworldGenRules(),
                (buf, aHelper, overworldGenRules) -> this.writeOverworldGenRules(buf, aHelper, overworldGenRules, strings));

        helper.writeOptionalNull(buffer, definitionChunkGen.getMultinoiseGenRules(), this::writeMultinoiseGenRules);

        helper.writeOptionalNull(buffer, definitionChunkGen.getLegacyWorldGenRules(),
                (buf, aHelper, legacyWorldGenRules) -> this.writeLegacyWorldGenRules(buf, aHelper, legacyWorldGenRules, strings));

        helper.writeOptionalNull(buffer, definitionChunkGen.getBiomeReplacements(), this::writeBiomeReplacementsData);

        helper.writeOptionalNull(buffer, definitionChunkGen.getVillageType(), (b, n) -> b.writeByte(n.ordinal()));

        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceBuilderData(), this::writeBiomeSurfaceBuilderData);

        helper.writeOptionalNull(buffer, definitionChunkGen.getSubsurfaceBuilderData(), this::writeBiomeSurfaceBuilderData);
    }

    protected void writeBiomeSurfaceBuilderData(ByteBuf buffer, BedrockCodecHelper helper, BiomeSurfaceBuilderData data) {
        helper.writeOptionalNull(buffer, data.getSurfaceMaterials(), this::writeSurfaceMaterial);
        buffer.writeBoolean(data.isHasDefaultOverworldSurface());
        buffer.writeBoolean(data.isHasSwampSurface());
        buffer.writeBoolean(data.isHasFrozenOceanSurface());
        buffer.writeBoolean(data.isHasTheEndSurface());
        helper.writeOptionalNull(buffer, data.getMesaSurface(), this::writeMesaSurface);
        helper.writeOptionalNull(buffer, data.getCappedSurface(), this::writeCappedSurface);
        helper.writeOptionalNull(buffer, data.getNoiseGradientSurface(), this::writeNoiseGradientSurface);
    }

    protected void writeNoiseGradientSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeNoiseGradientSurfaceData data) {
        helper.writeArray(buffer, data.getNonReplaceableBlocks(), ByteBuf::writeIntLE);
        helper.writeArray(buffer, data.getGradientBlocks(), (buf, val) ->
                buf.writeIntLE(val.getBlock()));
        helper.writeString(buffer, data.getNoise());
        buffer.writeIntLE(data.getFirstOctave());
        helper.writeArray(buffer, data.getAmplitudes(), ByteBuf::writeFloatLE);
    }

    @Override
    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);

        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));

        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);

        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));

        BiomeOverworldGenRulesData overworldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readOverworldGenRules(buf, aHelper, strings));

        BiomeMultinoiseGenRulesData multinoiseGenRules = helper.readOptional(buffer, null, this::readMultinoiseGenRules);

        BiomeLegacyWorldGenRulesData legacyWorldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readLegacyWorldGenRules(buf, aHelper, strings));

        List<BiomeReplacementData> replacementsData = helper.readOptional(buffer, null, this::readBiomeReplacementsData);

        VillageType villageType = helper.readOptional(buffer, null, buf -> VillageType.values()[buf.readUnsignedByte()]);

        BiomeSurfaceBuilderData surfaceBuilderData = helper.readOptional(buffer, null, this::readBiomeSurfaceBuilderData);

        BiomeSurfaceBuilderData subsurfaceBuilderData = helper.readOptional(buffer, null, this::readBiomeSurfaceBuilderData);

        return new BiomeDefinitionChunkGenData(climate, consolidatedFeatures,
                mountainParams, surfaceMaterialAdjustment,
                null, false, false,
                false, false,
                null, null,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules, replacementsData, villageType, surfaceBuilderData, subsurfaceBuilderData);
    }

    protected BiomeSurfaceBuilderData readBiomeSurfaceBuilderData(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeSurfaceMaterialData surfaceMaterials = helper.readOptional(buffer, null, this::readSurfaceMaterial);
        boolean hasDefaultOverworldSurface = buffer.readBoolean();
        boolean hasSwampSurface = buffer.readBoolean();
        boolean hasFrozenOceanSurface = buffer.readBoolean();
        boolean hasTheEndSurface = buffer.readBoolean();
        BiomeMesaSurfaceData mesaSurface = helper.readOptional(buffer, null, this::readMesaSurface);
        BiomeCappedSurfaceData cappedSurface = helper.readOptional(buffer, null, this::readCappedSurface);
        BiomeNoiseGradientSurfaceData noiseGradientSurface = helper.readOptional(buffer, null, this::readNoiseGradientSurface);

        return new BiomeSurfaceBuilderData(surfaceMaterials, hasDefaultOverworldSurface,hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface, mesaSurface, cappedSurface, noiseGradientSurface);
    }

    protected BiomeNoiseGradientSurfaceData readNoiseGradientSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        List<Integer> nonReplaceableBlocks = new ArrayList<>();
        helper.readArray(buffer, nonReplaceableBlocks, (buf, h) -> (int) buf.readUnsignedIntLE());
        List<NoiseBlockSpecifier> gradientBlocks = new ArrayList<>();
        helper.readArray(buffer, gradientBlocks, (buf, h) ->
                new NoiseBlockSpecifier(null, 0, 0, 0, (int) buf.readUnsignedIntLE()));
        String noiseSeedString = helper.readString(buffer);
        int firstOctave = buffer.readIntLE();
        List<Float> amplitudes = new ArrayList<>();
        helper.readArray(buffer, amplitudes, (buf, h) -> buf.readFloatLE());
        return new BiomeNoiseGradientSurfaceData(nonReplaceableBlocks, gradientBlocks, noiseSeedString, firstOctave, amplitudes);
    }
}
