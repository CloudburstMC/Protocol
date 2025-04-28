package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.packet.BiomeDefinitionListPacket;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.*;
import java.util.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiomeDefinitionListSerializer_v800 implements BedrockPacketSerializer<BiomeDefinitionListPacket> {

    public static final BiomeDefinitionListSerializer_v800 INSTANCE = new BiomeDefinitionListSerializer_v800();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        Map<String, BiomeDefinitionData> biomes = packet.getBiomes();
        helper.writeArray(buffer, biomes.values(), new TriConsumer<ByteBuf, BedrockCodecHelper, BiomeDefinitionData>() {
            private int index = 0;

            @Override
            public void accept(ByteBuf byteBuf, BedrockCodecHelper aHelper, BiomeDefinitionData definition) {
                byteBuf.writeIntLE(index++);
                writeDefinition(buffer, aHelper, definition);
            }
        });
        helper.writeArray(buffer, biomes.keySet(), (byteBuf, bedrockCodecHelper, biomeName) -> bedrockCodecHelper.writeString(byteBuf, biomeName));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        List<IntObjectPair<BiomeDefinitionData>> biomeDefinitions = new ArrayList<>();
        helper.readArray(buffer, biomeDefinitions, (byteBuf, bedrockCodecHelper) -> {
            int index = byteBuf.readShortLE();
            return IntObjectPair.of(index, readDefinition(byteBuf, bedrockCodecHelper));
        });
        List<String> biomeNames = new ArrayList<>();
        helper.readArray(buffer, biomeNames,
                (byteBuf, bedrockCodecHelper) -> bedrockCodecHelper.readString(byteBuf));

        Map<String, BiomeDefinitionData> biomes = new LinkedHashMap<>();

        for (IntObjectPair<BiomeDefinitionData> pair : biomeDefinitions) {
            int index = pair.firstInt();
            BiomeDefinitionData definition = pair.second();
            String name = biomeNames.get(index);
            biomes.put(name, definition);
        }
        packet.setBiomes(biomes);
    }

    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition) {
        //noinspection OptionalGetWithoutIsPresent
        helper.writeOptional(buffer, OptionalInt::isPresent, definition.getId(), (buf, optionalInt) -> buf.writeShortLE(optionalInt.getAsInt()));
        buffer.writeFloatLE(definition.getTemperature());
        buffer.writeFloatLE(definition.getDownfall());
        buffer.writeFloatLE(definition.getRedSporeDensity());
        buffer.writeFloatLE(definition.getBlueSporeDensity());
        buffer.writeFloatLE(definition.getAshDensity());
        buffer.writeFloatLE(definition.getWhiteAshDensity());
        buffer.writeFloatLE(definition.getDepth());
        buffer.writeFloatLE(definition.getScale());
        buffer.writeIntLE(definition.getMapWaterColor().getRGB());
        buffer.writeBoolean(definition.isRain());
        helper.writeOptionalNull(buffer, definition.getTags(), (byteBuf, aHelper, tags) -> {
            VarInts.writeUnsignedInt(byteBuf, tags.length);
            for (int tag : tags) {
                byteBuf.writeByte(tag);
            }
        });
        helper.writeOptionalNull(buffer, definition.getChunkGenData(), (byteBuf, chunkGenData) -> {
            writeDefinitionChunkGen(byteBuf, helper, chunkGenData);
        });
    }

    protected BiomeDefinitionData readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        OptionalInt id = helper.readOptional(buffer, OptionalInt.empty(), buf -> OptionalInt.of(buf.readUnsignedShortLE()));
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float redSporeDensity = buffer.readFloatLE();
        float blueSporeDensity = buffer.readFloatLE();
        float ashDensity = buffer.readFloatLE();
        float whiteAshDensity = buffer.readFloatLE();
        float depth = buffer.readFloatLE();
        float scale = buffer.readFloatLE();
        Color mapWaterColor = new Color(buffer.readIntLE(), true);
        boolean rain = buffer.readBoolean();


        int[] tags = helper.readOptional(buffer, null, byteBuf -> {
            int length = VarInts.readUnsignedInt(buffer);
            Preconditions.checkArgument(buffer.isReadable(length * 2), "Not enough readable bytes for tags");
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = byteBuf.readUnsignedShortLE();
            }
            return array;
        });

        BiomeDefinitionChunkGenData chunkGenData = helper.readOptional(buffer, null, this::readDefinitionChunkGen);

        return new BiomeDefinitionData(id, temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, depth, scale, mapWaterColor,
                rain, tags, chunkGenData);
    }

    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen) {
        helper.writeOptionalNull(buffer, definitionChunkGen.getClimate(), this::writeClimate);
        helper.writeOptionalNull(buffer, definitionChunkGen.getConsolidatedFeatures(), this::writeConsolidatedFeatures);
        helper.writeOptionalNull(buffer, definitionChunkGen.getMountainParams(), this::writeMountainParamsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceMaterialAdjustment(), this::writeSurfaceMaterialAdjustment);
        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceMaterial(), this::writeSurfaceMaterial);
        buffer.writeBoolean(definitionChunkGen.isHasSwampSurface());
        buffer.writeBoolean(definitionChunkGen.isHasFrozenOceanSurface());
        buffer.writeBoolean(definitionChunkGen.isHasTheEndSurface());
        helper.writeOptionalNull(buffer, definitionChunkGen.getMesaSurface(), this::writeMesaSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.getCappedSurface(), this::writeCappedSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.getOverworldGenRules(), this::writeOverworldGenRules);
        helper.writeOptionalNull(buffer, definitionChunkGen.getMultinoiseGenRules(), this::writeMultinoiseGenRules);
        helper.writeOptionalNull(buffer, definitionChunkGen.getLegacyWorldGenRules(), this::writeLegacyWorldGenRules);
    }

    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null, this::readConsolidatedFeatures);
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null, this::readSurfaceMaterialAdjustment);
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
        boolean hasSwampSurface = buffer.readBoolean();
        boolean hasFrozenOceanSurface = buffer.readBoolean();
        boolean hasTheEndSurface = buffer.readBoolean();
        BiomeMesaSurfaceData mesaSurface = helper.readOptional(buffer, null, this::readMesaSurface);
        BiomeCappedSurfaceData cappedSurface = helper.readOptional(buffer, null, this::readCappedSurface);
        BiomeOverworldGenRulesData overworldGenRules = helper.readOptional(buffer, null, this::readOverworldGenRules);
        BiomeMultinoiseGenRulesData multinoiseGenRules = helper.readOptional(buffer, null, this::readMultinoiseGenRules);
        BiomeLegacyWorldGenRulesData legacyWorldGenRules = helper.readOptional(buffer, null, this::readLegacyWorldGenRules);

        return new BiomeDefinitionChunkGenData(climate, consolidatedFeatures,
                mountainParams, surfaceMaterialAdjustment,
                surfaceMaterial, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface,
                mesaSurface, cappedSurface,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules);
    }

    protected void writeClimate(ByteBuf buffer, BedrockCodecHelper helper, BiomeClimateData climate) {
        buffer.writeFloatLE(climate.getTemperature());
        buffer.writeFloatLE(climate.getDownfall());
        buffer.writeFloatLE(climate.getRedSporeDensity());
        buffer.writeFloatLE(climate.getBlueSporeDensity());
        buffer.writeFloatLE(climate.getAshDensity());
        buffer.writeFloatLE(climate.getWhiteAshDensity());
        buffer.writeFloatLE(climate.getSnowAccumulationMin());
        buffer.writeFloatLE(climate.getSnowAccumulationMax());
    }

    protected BiomeClimateData readClimate(ByteBuf buffer, BedrockCodecHelper helper) {
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float redSporeDensity = buffer.readFloatLE();
        float blueSporeDensity = buffer.readFloatLE();
        float ashDensity = buffer.readFloatLE();
        float whiteAshDensity = buffer.readFloatLE();
        float snowAccumulationMin = buffer.readFloatLE();
        float snowAccumulationMax = buffer.readFloatLE();

        return new BiomeClimateData(temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, snowAccumulationMin, snowAccumulationMax);
    }

    protected void writeConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper, List<BiomeConsolidatedFeatureData> consolidatedFeatures) {
        helper.writeArray(buffer, consolidatedFeatures, this::writeConsolidatedFeature);
    }

    protected List<BiomeConsolidatedFeatureData> readConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = new ObjectArrayList<>();
        helper.readArray(buffer, consolidatedFeatures, this::readConsolidatedFeature);
        return consolidatedFeatures;
    }

    protected void writeConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper, BiomeConsolidatedFeatureData consolidatedFeature) {
        this.writeScatterParam(buffer, helper, consolidatedFeature.getScatter());
        buffer.writeShortLE(consolidatedFeature.getFeature());
        buffer.writeShortLE(consolidatedFeature.getIdentifier());
        buffer.writeShortLE(consolidatedFeature.getPass());
        buffer.writeBoolean(consolidatedFeature.isInternalUse());
    }

    protected BiomeConsolidatedFeatureData readConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeScatterParamData scatter = readScatterParam(buffer, helper);
        int feature = buffer.readShortLE();
        int identifier = buffer.readShortLE();
        int pass = buffer.readShortLE();
        boolean internalUse = buffer.readBoolean();

        return new BiomeConsolidatedFeatureData(scatter, feature, identifier, pass, internalUse);
    }

    protected void writeScatterParam(ByteBuf buffer, BedrockCodecHelper helper, BiomeScatterParamData scatterParam) {
        helper.writeArray(buffer, scatterParam.getCoordinates(), this::writeCoordinate);
        VarInts.writeInt(buffer, scatterParam.getEvalOrder());
        VarInts.writeInt(buffer, scatterParam.getChancePercentType());
        buffer.writeShortLE(scatterParam.getChancePercent());
        buffer.writeIntLE(scatterParam.getChanceNumerator());
        buffer.writeIntLE(scatterParam.getChangeDenominator());
        VarInts.writeInt(buffer, scatterParam.getIterationsType());
        buffer.writeShortLE(scatterParam.getIterations());
    }

    protected BiomeScatterParamData readScatterParam(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeCoordinateData> coordinates = new ObjectArrayList<>();
        helper.readArray(buffer, coordinates, this::readCoordinate);
        int evalOrder = VarInts.readInt(buffer);
        int chancePercentType = VarInts.readInt(buffer);
        int chancePercent = buffer.readShortLE();
        int chanceNumerator = buffer.readIntLE();
        int chanceDenominator = buffer.readIntLE();
        int iterationsType = VarInts.readInt(buffer);
        int iterations = buffer.readShortLE();

        return new BiomeScatterParamData(coordinates, evalOrder, chancePercentType,
                chancePercent, chanceNumerator, chanceDenominator,
                iterationsType, iterations);
    }

    protected void writeCoordinate(ByteBuf buffer, BedrockCodecHelper helper, BiomeCoordinateData coordinate) {
        VarInts.writeInt(buffer, coordinate.getMinValueType());
        buffer.writeShortLE(coordinate.getMinValue());
        VarInts.writeInt(buffer, coordinate.getMaxValueType());
        buffer.writeShortLE(coordinate.getMaxValue());
        buffer.writeIntLE((int) coordinate.getGridOffset());
        buffer.writeIntLE((int) coordinate.getGridStepSize());
        VarInts.writeInt(buffer, coordinate.getDistribution());
    }

    protected BiomeCoordinateData readCoordinate(ByteBuf buffer, BedrockCodecHelper helper) {
        int minValueType = VarInts.readInt(buffer);
        int minValue = buffer.readShortLE();
        int maxValueType = VarInts.readInt(buffer);
        int maxValue = buffer.readShortLE();
        int gridOffset = buffer.readIntLE();
        int gridStepSize = buffer.readIntLE();
        int distribution = VarInts.readInt(buffer);

        return new BiomeCoordinateData(minValueType, minValue, maxValueType,
                maxValue, gridOffset, gridStepSize, distribution);
    }

    protected void writeMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper, BiomeMountainParamsData mountainParams) {
        buffer.writeIntLE(mountainParams.getSteepBlock());
        buffer.writeBoolean(mountainParams.isNorthSlopes());
        buffer.writeBoolean(mountainParams.isSouthSlopes());
        buffer.writeBoolean(mountainParams.isWestSlopes());
        buffer.writeBoolean(mountainParams.isEastSlopes());
        buffer.writeBoolean(mountainParams.isTopSlideEnabled());
    }

    protected BiomeMountainParamsData readMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper) {
        int steepBlock = buffer.readIntLE();
        boolean northSlopes = buffer.readBoolean();
        boolean southSlopes = buffer.readBoolean();
        boolean westSlopes = buffer.readBoolean();
        boolean eastSlopes = buffer.readBoolean();
        boolean topSlideEnabled = buffer.readBoolean();

        return new BiomeMountainParamsData(steepBlock, northSlopes, southSlopes,
                westSlopes, eastSlopes, topSlideEnabled);
    }

    protected void writeSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper, BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment) {
        helper.writeArray(buffer, surfaceMaterialAdjustment.getBiomeElements(), this::writeBiomeElement);
    }

    protected BiomeSurfaceMaterialAdjustmentData readSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeElementData> biomeElements = new ObjectArrayList<>();
        helper.readArray(buffer, biomeElements, this::readBiomeElement);
        return new BiomeSurfaceMaterialAdjustmentData(biomeElements);
    }

    protected void writeBiomeElement(ByteBuf buffer, BedrockCodecHelper helper, BiomeElementData biomeElement) {
        buffer.writeFloatLE(biomeElement.getNoiseFrequencyScale());
        buffer.writeFloatLE(biomeElement.getNoiseLowerBound());
        buffer.writeFloatLE(biomeElement.getNoiseUpperBound());
        VarInts.writeInt(buffer, biomeElement.getHeightMinType());
        buffer.writeShortLE(biomeElement.getHeightMin());
        VarInts.writeInt(buffer, biomeElement.getHeightMaxType());
        buffer.writeShortLE(biomeElement.getHeightMax());
        this.writeSurfaceMaterial(buffer, helper, biomeElement.getAdjustedMaterials());
    }

    protected BiomeElementData readBiomeElement(ByteBuf buffer, BedrockCodecHelper helper) {
        float noiseFrequencyScale = buffer.readFloatLE();
        float noiseLowerBound = buffer.readFloatLE();
        float noiseUpperBound = buffer.readFloatLE();
        int heightMinType = VarInts.readInt(buffer);
        int heightMin = buffer.readShortLE();
        int heightMaxType = VarInts.readInt(buffer);
        int heightMax = buffer.readShortLE();
        BiomeSurfaceMaterialData adjustedMaterials = readSurfaceMaterial(buffer, helper);

        return new BiomeElementData(noiseFrequencyScale, noiseLowerBound, noiseUpperBound,
                heightMinType, heightMin, heightMaxType, heightMax, adjustedMaterials);
    }

    protected void writeSurfaceMaterial(ByteBuf buffer, BedrockCodecHelper helper, BiomeSurfaceMaterialData surfaceMaterial) {
        this.writeBlock(buffer, helper, surfaceMaterial.getTopBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.getMidBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.getSeaFloorBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.getFoundationBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.getSeaBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.getSeaFloorDepth());
    }

    protected BiomeSurfaceMaterialData readSurfaceMaterial(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition topBlock = helper.readOptional(buffer, null, this::readBlock);
        BlockDefinition midBlock = helper.readOptional(buffer, null, byteBuf -> readBlock(byteBuf, helper));
        BlockDefinition seaFloorBlock = helper.readOptional(buffer, null, byteBuf -> readBlock(byteBuf, helper));
        BlockDefinition foundationBlock = helper.readOptional(buffer, null, byteBuf -> readBlock(byteBuf, helper));
        BlockDefinition seaBlock = helper.readOptional(buffer, null, byteBuf -> readBlock(byteBuf, helper));
        BlockDefinition seaFloorDepth = helper.readOptional(buffer, null, byteBuf -> readBlock(byteBuf, helper));

        return new BiomeSurfaceMaterialData(topBlock, midBlock, seaFloorBlock, foundationBlock, seaBlock, seaFloorDepth);
    }


    protected void writeMesaSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeMesaSurfaceData mesaSurface) {
        buffer.writeIntLE((int) mesaSurface.getClayMaterial());
        buffer.writeIntLE((int) mesaSurface.getHardClayMaterial());
        buffer.writeBoolean(mesaSurface.isBrycePillars());
        buffer.writeBoolean(mesaSurface.isHasForest());
    }

    protected BiomeMesaSurfaceData readMesaSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        int clayMaterial = buffer.readIntLE();
        int hardClayMaterial = buffer.readIntLE();
        boolean brycePillars = buffer.readBoolean();
        boolean hasForest = buffer.readBoolean();

        return new BiomeMesaSurfaceData(clayMaterial, hardClayMaterial, brycePillars, hasForest);
    }


    protected void writeCappedSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeCappedSurfaceData cappedSurface) {
        helper.writeArray(buffer, cappedSurface.getFloorBlocks(), this::writeBlock);
        helper.writeArray(buffer, cappedSurface.getCeilingBlocks(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.getSeaBlock(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.getFoundationBlock(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.getBeachBlock(), this::writeBlock);
    }

    protected BiomeCappedSurfaceData readCappedSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BlockDefinition> floorBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, floorBlocks, this::readBlock);
        List<BlockDefinition> ceilingBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, ceilingBlocks, this::readBlock);
        BlockDefinition seaBlock = helper.readOptional(buffer, null, this::readBlock);
        BlockDefinition foundationBlock = helper.readOptional(buffer, null, this::readBlock);
        BlockDefinition beachBlock = helper.readOptional(buffer, null, this::readBlock);

        return new BiomeCappedSurfaceData(floorBlocks, ceilingBlocks, seaBlock, foundationBlock, beachBlock);
    }

    protected void writeOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeOverworldGenRulesData overworldGenRules) {
        helper.writeArray(buffer, overworldGenRules.getHillsTransformations(), this::writeWeight);
        helper.writeArray(buffer, overworldGenRules.getMutateTransformations(), this::writeWeight);
        helper.writeArray(buffer, overworldGenRules.getRiverTransformations(), this::writeWeight);
        helper.writeArray(buffer, overworldGenRules.getShoreTransformations(), this::writeWeight);
        helper.writeArray(buffer, overworldGenRules.getPreHillsEdgeTransformations(), this::writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.getPostShoreTransformations(), this::writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.getClimateTransformations(), this::writeWeightedTemperature);
    }

    protected BiomeOverworldGenRulesData readOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeWeightedData> hillsTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, hillsTransformations, this::readWeight);
        List<BiomeWeightedData> mutateTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, mutateTransformations, this::readWeight);
        List<BiomeWeightedData> riverTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, riverTransformations, this::readWeight);
        List<BiomeWeightedData> shoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, shoreTransformations, this::readWeight);
        List<BiomeConditionalTransformationData> preHillsEdgeTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, preHillsEdgeTransformations, this::readConditionalTransformation);
        List<BiomeConditionalTransformationData> postShoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, postShoreTransformations, this::readConditionalTransformation);
        List<BiomeWeightedTemperatureData> climateTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, climateTransformations, this::readWeightedTemperature);

        return new BiomeOverworldGenRulesData(hillsTransformations,
                mutateTransformations,
                riverTransformations,
                shoreTransformations,
                preHillsEdgeTransformations,
                postShoreTransformations,
                climateTransformations);
    }

    protected void writeWeight(ByteBuf buffer, BedrockCodecHelper helper, BiomeWeightedData weightedData) {
        buffer.writeShortLE(weightedData.getBiome());
        buffer.writeIntLE((int) weightedData.getWeight());
    }

    protected BiomeWeightedData readWeight(ByteBuf buffer, BedrockCodecHelper helper) {
        int biome = buffer.readShortLE();
        int weight = buffer.readIntLE();
        return new BiomeWeightedData(biome, weight);
    }

    protected void writeConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper, BiomeConditionalTransformationData conditionalTransformation) {
        helper.writeArray(buffer, conditionalTransformation.getWeightedBiomes(), this::writeWeight);
        buffer.writeShortLE(conditionalTransformation.getConditionJson());
        buffer.writeIntLE((int) conditionalTransformation.getMinPassingNeighbors());
    }

    protected BiomeConditionalTransformationData readConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeWeightedData> weightedBiomes = new ObjectArrayList<>();
        helper.readArray(buffer, weightedBiomes, this::readWeight);
        int conditionJson = buffer.readShortLE();
        int minPassingNeighbors = buffer.readIntLE();
        return new BiomeConditionalTransformationData(weightedBiomes, conditionJson, minPassingNeighbors);
    }

    protected void writeWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper, BiomeWeightedTemperatureData weightedTemperature) {
        VarInts.writeInt(buffer, weightedTemperature.getTemperature());
        buffer.writeIntLE((int) weightedTemperature.getWeight());
    }

    protected BiomeWeightedTemperatureData readWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper) {
        int temperature = VarInts.readInt(buffer);
        int weight = buffer.readIntLE();
        return new BiomeWeightedTemperatureData(temperature, weight);
    }

    protected void writeMultinoiseGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeMultinoiseGenRulesData multinoiseGenRules) {
        buffer.writeFloatLE(multinoiseGenRules.getTemperature());
        buffer.writeFloatLE(multinoiseGenRules.getHumidity());
        buffer.writeFloatLE(multinoiseGenRules.getAltitude());
        buffer.writeFloatLE(multinoiseGenRules.getWeirdness());
        buffer.writeFloatLE(multinoiseGenRules.getWeight());
    }

    protected BiomeMultinoiseGenRulesData readMultinoiseGenRules(ByteBuf buffer, BedrockCodecHelper helper) {
        float temperature = buffer.readFloatLE();
        float humidity = buffer.readFloatLE();
        float altitude = buffer.readFloatLE();
        float weirdness = buffer.readFloatLE();
        float weight = buffer.readFloatLE();

        return new BiomeMultinoiseGenRulesData(temperature, humidity, altitude, weirdness, weight);
    }

    protected void writeLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeLegacyWorldGenRulesData legacyWorldGenRules) {
        helper.writeArray(buffer, legacyWorldGenRules.getLegacyPreHills(), this::writeConditionalTransformation);
    }

    protected BiomeLegacyWorldGenRulesData readLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeConditionalTransformationData> legacyPreHills = new ObjectArrayList<>();
        helper.readArray(buffer, legacyPreHills, this::readConditionalTransformation);
        return new BiomeLegacyWorldGenRulesData(legacyPreHills);
    }

    protected void writeBlock(ByteBuf buffer, BedrockCodecHelper helper, BlockDefinition blockDefinition) {
        helper.getBlockDefinitions().isRegistered(blockDefinition);
        buffer.writeIntLE(blockDefinition.getRuntimeId());
    }

    protected BlockDefinition readBlock(ByteBuf buffer, BedrockCodecHelper helper) {
        return helper.getBlockDefinitions().getDefinition(buffer.readIntLE());
    }
}
