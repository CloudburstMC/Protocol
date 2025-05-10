package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.CoordinateEvaluationOrder;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.bedrock.data.RandomDistributionType;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.packet.BiomeDefinitionListPacket;
import org.cloudburstmc.protocol.common.util.*;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Indexed;
import org.cloudburstmc.protocol.common.util.index.IndexedList;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuppressWarnings("deprecation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiomeDefinitionListSerializer_v800 implements BedrockPacketSerializer<BiomeDefinitionListPacket> {

    public static final BiomeDefinitionListSerializer_v800 INSTANCE = new BiomeDefinitionListSerializer_v800();

    private static final BiomeTemperatureCategory[] TEMPERATURE_CATEGORIES = BiomeTemperatureCategory.values();
    private static final ExpressionOp[] EXPRESSION_OPS = ExpressionOp.values();
    private static final CoordinateEvaluationOrder[] EVALUATION_ORDERS = CoordinateEvaluationOrder.values();
    private static final RandomDistributionType[] RANDOM_DISTRIBUTION_TYPES = RandomDistributionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        SequencedHashSet<String> strings = new SequencedHashSet<>();
        BiomeDefinitions biomeDefinitions = packet.getBiomes();
        Map<String, BiomeDefinitionData> biomes = biomeDefinitions.getDefinitions();
        helper.writeArray(buffer, biomes.entrySet(), (byteBuf, aHelper, entry) -> {
            String name = entry.getKey();
            BiomeDefinitionData definition = entry.getValue();
            byteBuf.writeShortLE(strings.addAndGetIndex(name));
            writeDefinition(byteBuf, aHelper, definition, strings);
        });
        helper.writeArray(buffer, strings, (byteBuf, bedrockCodecHelper, biomeName) -> bedrockCodecHelper.writeString(byteBuf, biomeName));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        List<String> strings = new ObjectArrayList<>();
        List<IntObjectPair<BiomeDefinitionData>> biomeDefinitions = new ObjectArrayList<>();
        helper.readArray(buffer, biomeDefinitions, (byteBuf, bedrockCodecHelper) -> {
            int index = byteBuf.readUnsignedShortLE();
            return IntObjectPair.of(index, readDefinition(byteBuf, bedrockCodecHelper, strings));
        });
        IndexedBiomes indexedBiomes = new IndexedBiomes(biomeDefinitions, strings);

        helper.readArray(buffer, strings,
                (byteBuf, bedrockCodecHelper) -> bedrockCodecHelper.readString(byteBuf));
        packet.setBiomes(new BiomeDefinitions(indexedBiomes));
    }

    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition, SequencedHashSet<String> strings) {
        helper.writeOptional(buffer, Objects::nonNull, definition.getId(), (buf, id) -> buf.writeShortLE(strings.addAndGetIndex(id)));
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
            VarInts.writeUnsignedInt(byteBuf, tags.size());
            for (String tag : tags) {
                byteBuf.writeShortLE(strings.addAndGetIndex(tag));
            }
        });
        helper.writeOptionalNull(buffer, definition.getChunkGenData(),
                (buf, aHelper, data) -> writeDefinitionChunkGen(buf, aHelper, data, strings));
    }

    protected BiomeDefinitionData readDefinition(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Indexed<String> id = helper.readOptional(buffer, null,
                (buf, aHelper) -> new Indexed<>(strings, buf.readUnsignedShortLE()));
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


        IndexedList<String> tags = helper.readOptional(buffer, null, byteBuf -> {
            int length = VarInts.readUnsignedInt(byteBuf);
            Preconditions.checkArgument(byteBuf.isReadable(length * 2), "Not enough readable bytes for tags");
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = byteBuf.readUnsignedShortLE();
            }
            return new IndexedList<>(strings, array);
        });

        BiomeDefinitionChunkGenData chunkGenData = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readDefinitionChunkGen(buf, aHelper, strings));

        return new BiomeDefinitionData(id, temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, depth, scale, mapWaterColor,
                rain, tags, chunkGenData);
    }

    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen,
                                           SequencedHashSet<String> strings) {
        helper.writeOptionalNull(buffer, definitionChunkGen.getClimate(), this::writeClimate);
        helper.writeOptionalNull(buffer, definitionChunkGen.getConsolidatedFeatures(),
                (buf, aHelper, consolidatedFeatures) -> this.writeConsolidatedFeatures(buf, aHelper, consolidatedFeatures, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.getMountainParams(), this::writeMountainParamsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceMaterialAdjustment(),
                (buf, aHelper, surfaceMaterialAdjustment) -> this.writeSurfaceMaterialAdjustment(buf, aHelper, surfaceMaterialAdjustment, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.getSurfaceMaterial(), this::writeSurfaceMaterial);
        buffer.writeBoolean(definitionChunkGen.isHasSwampSurface());
        buffer.writeBoolean(definitionChunkGen.isHasFrozenOceanSurface());
        buffer.writeBoolean(definitionChunkGen.isHasTheEndSurface());
        helper.writeOptionalNull(buffer, definitionChunkGen.getMesaSurface(), this::writeMesaSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.getCappedSurface(), this::writeCappedSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.getOverworldGenRules(),
                (buf, aHelper, overworldGenRules) -> this.writeOverworldGenRules(buf, aHelper, overworldGenRules, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.getMultinoiseGenRules(), this::writeMultinoiseGenRules);
        helper.writeOptionalNull(buffer, definitionChunkGen.getLegacyWorldGenRules(),
                (buf, aHelper, legacyWorldGenRules) -> this.writeLegacyWorldGenRules(buf, aHelper, legacyWorldGenRules, strings));
    }

    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
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

    protected void writeConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper, List<BiomeConsolidatedFeatureData> consolidatedFeatures,
                                             SequencedHashSet<String> strings) {
        helper.writeArray(buffer, consolidatedFeatures,
                (buf, aHelper, consolidatedFeature) -> this.writeConsolidatedFeature(buf, aHelper, consolidatedFeature, strings));
    }

    protected List<BiomeConsolidatedFeatureData> readConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = new ObjectArrayList<>();
        helper.readArray(buffer, consolidatedFeatures,
                (buf, aHelper) -> this.readConsolidatedFeature(buf, aHelper, strings));
        return consolidatedFeatures;
    }

    protected void writeConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper,
                                            BiomeConsolidatedFeatureData consolidatedFeature,
                                            SequencedHashSet<String> strings) {
        this.writeScatterParam(buffer, helper, consolidatedFeature.getScatter(), strings);
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.getFeature()));
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.getIdentifier()));
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.getPass()));
        buffer.writeBoolean(consolidatedFeature.isInternalUse());
    }

    protected BiomeConsolidatedFeatureData readConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeScatterParamData scatter = readScatterParam(buffer, helper, strings);
        Indexed<String> feature = new Indexed<>(strings, buffer.readShortLE());
        Indexed<String> identifier = new Indexed<>(strings, buffer.readShortLE());
        Indexed<String> pass = new Indexed<>(strings, buffer.readShortLE());
        boolean internalUse = buffer.readBoolean();

        return new BiomeConsolidatedFeatureData(scatter, feature, identifier, pass, internalUse);
    }

    protected void writeScatterParam(ByteBuf buffer, BedrockCodecHelper helper, BiomeScatterParamData scatterParam,
                                     SequencedHashSet<String> strings) {
        helper.writeArray(buffer, scatterParam.getCoordinates(),
                (buf, aHelper, coordinate) -> this.writeCoordinate(buf, aHelper, coordinate, strings));
        VarInts.writeInt(buffer, scatterParam.getEvalOrder().ordinal());
        VarInts.writeInt(buffer, scatterParam.getChancePercentType() == null ? -1 : scatterParam.getChancePercentType().ordinal());
        buffer.writeShortLE(strings.addAndGetIndex(scatterParam.getChancePercent()));
        buffer.writeIntLE(scatterParam.getChanceNumerator());
        buffer.writeIntLE(scatterParam.getChangeDenominator());
        VarInts.writeInt(buffer, scatterParam.getIterationsType() == null ? -1 : scatterParam.getIterationsType().ordinal());
        buffer.writeShortLE(strings.addAndGetIndex(scatterParam.getIterations()));
    }

    protected BiomeScatterParamData readScatterParam(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        List<BiomeCoordinateData> coordinates = new ObjectArrayList<>();
        helper.readArray(buffer, coordinates,
                (buf, aHelper) -> this.readCoordinate(buf, aHelper, strings));
        CoordinateEvaluationOrder evalOrder = EVALUATION_ORDERS[VarInts.readInt(buffer)];
        int chancePercentTypeInt = VarInts.readInt(buffer);
        ExpressionOp chancePercentType = chancePercentTypeInt == -1 ? null : EXPRESSION_OPS[chancePercentTypeInt];
        Indexed<String> chancePercent = new Indexed<>(strings, buffer.readShortLE());
        int chanceNumerator = buffer.readIntLE();
        int chanceDenominator = buffer.readIntLE();
        int iterationTypeInt = VarInts.readInt(buffer);
        ExpressionOp iterationsType = iterationTypeInt == -1 ? null : EXPRESSION_OPS[iterationTypeInt];
        Indexed<String> iterations = new Indexed<>(strings, buffer.readShortLE());

        return new BiomeScatterParamData(coordinates, evalOrder, chancePercentType,
                chancePercent, chanceNumerator, chanceDenominator,
                iterationsType, iterations);
    }

    protected void writeCoordinate(ByteBuf buffer, BedrockCodecHelper helper, BiomeCoordinateData coordinate,
                                   SequencedHashSet<String> strings) {
        this.writeExpressionOp(buffer, coordinate.getMinValueType());
        buffer.writeShortLE(strings.addAndGetIndex(coordinate.getMinValue()));
        this.writeExpressionOp(buffer, coordinate.getMaxValueType());
        buffer.writeShortLE(strings.addAndGetIndex(coordinate.getMaxValue()));
        buffer.writeIntLE((int) coordinate.getGridOffset());
        buffer.writeIntLE((int) coordinate.getGridStepSize());
        VarInts.writeInt(buffer, coordinate.getDistribution().ordinal());
    }

    protected BiomeCoordinateData readCoordinate(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        ExpressionOp minValueType = this.readExpressionOp(buffer);
        Indexed<String> minValue = new Indexed<>(strings, buffer.readShortLE());
        ExpressionOp maxValueType = this.readExpressionOp(buffer);
        Indexed<String> maxValue = new Indexed<>(strings, buffer.readShortLE());
        long gridOffset = buffer.readUnsignedIntLE();
        long gridStepSize = buffer.readUnsignedIntLE();
        RandomDistributionType distribution = RANDOM_DISTRIBUTION_TYPES[VarInts.readInt(buffer)];

        return new BiomeCoordinateData(minValueType, minValue, maxValueType,
                maxValue, gridOffset, gridStepSize, distribution);
    }

    protected void writeMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper, BiomeMountainParamsData mountainParams) {
        this.writeBlock(buffer, helper, mountainParams.getSteepBlock());
        buffer.writeBoolean(mountainParams.isNorthSlopes());
        buffer.writeBoolean(mountainParams.isSouthSlopes());
        buffer.writeBoolean(mountainParams.isWestSlopes());
        buffer.writeBoolean(mountainParams.isEastSlopes());
        buffer.writeBoolean(mountainParams.isTopSlideEnabled());
    }

    protected BiomeMountainParamsData readMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition steepBlock = this.readBlock(buffer, helper);
        boolean northSlopes = buffer.readBoolean();
        boolean southSlopes = buffer.readBoolean();
        boolean westSlopes = buffer.readBoolean();
        boolean eastSlopes = buffer.readBoolean();
        boolean topSlideEnabled = buffer.readBoolean();

        return new BiomeMountainParamsData(steepBlock, northSlopes, southSlopes,
                westSlopes, eastSlopes, topSlideEnabled);
    }

    protected void writeSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper,
                                                  BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment,
                                                  SequencedHashSet<String> strings) {
        helper.writeArray(buffer, surfaceMaterialAdjustment.getBiomeElements(),
                (buf, aHelper, biomeElement) -> this.writeBiomeElement(buf, aHelper, biomeElement, strings));
    }

    protected BiomeSurfaceMaterialAdjustmentData readSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper,
                                                                                List<String> strings) {
        List<BiomeElementData> biomeElements = new ObjectArrayList<>();
        helper.readArray(buffer, biomeElements,
                (buf, aHelper) -> this.readBiomeElement(buf, aHelper, strings));
        return new BiomeSurfaceMaterialAdjustmentData(biomeElements);
    }

    protected void writeBiomeElement(ByteBuf buffer, BedrockCodecHelper helper, BiomeElementData biomeElement,
                                     SequencedHashSet<String> strings) {
        buffer.writeFloatLE(biomeElement.getNoiseFrequencyScale());
        buffer.writeFloatLE(biomeElement.getNoiseLowerBound());
        buffer.writeFloatLE(biomeElement.getNoiseUpperBound());
        this.writeExpressionOp(buffer, biomeElement.getHeightMinType());
        buffer.writeShortLE(strings.addAndGetIndex(biomeElement.getHeightMin()));
        this.writeExpressionOp(buffer, biomeElement.getHeightMaxType());
        buffer.writeShortLE(strings.addAndGetIndex(biomeElement.getHeightMax()));
        this.writeSurfaceMaterial(buffer, helper, biomeElement.getAdjustedMaterials());
    }

    protected BiomeElementData readBiomeElement(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        float noiseFrequencyScale = buffer.readFloatLE();
        float noiseLowerBound = buffer.readFloatLE();
        float noiseUpperBound = buffer.readFloatLE();
        ExpressionOp heightMinType = this.readExpressionOp(buffer);
        Indexed<String> heightMin = new Indexed<>(strings, buffer.readShortLE());
        ExpressionOp heightMaxType = this.readExpressionOp(buffer);
        Indexed<String> heightMax = new Indexed<>(strings, buffer.readShortLE());
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
        buffer.writeIntLE(surfaceMaterial.getSeaFloorDepth());
    }

    protected BiomeSurfaceMaterialData readSurfaceMaterial(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition topBlock = this.readBlock(buffer, helper);
        BlockDefinition midBlock = this.readBlock(buffer, helper);
        BlockDefinition seaFloorBlock = this.readBlock(buffer, helper);
        BlockDefinition foundationBlock = this.readBlock(buffer, helper);
        BlockDefinition seaBlock = this.readBlock(buffer, helper);
        int seaFloorDepth = buffer.readIntLE();

        return new BiomeSurfaceMaterialData(topBlock, midBlock, seaFloorBlock, foundationBlock, seaBlock, seaFloorDepth);
    }


    protected void writeMesaSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeMesaSurfaceData mesaSurface) {
        this.writeBlock(buffer, helper, mesaSurface.getClayMaterial());
        this.writeBlock(buffer, helper, mesaSurface.getHardClayMaterial());
        buffer.writeBoolean(mesaSurface.isBrycePillars());
        buffer.writeBoolean(mesaSurface.isHasForest());
    }

    protected BiomeMesaSurfaceData readMesaSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition clayMaterial = this.readBlock(buffer, helper);
        BlockDefinition hardClayMaterial = this.readBlock(buffer, helper);
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

    protected void writeOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper,
                                          BiomeOverworldGenRulesData overworldGenRules, SequencedHashSet<String> strings) {
        BiConsumer<ByteBuf, BiomeWeightedData> writeWeight =
                (buf, data) -> this.writeWeight(buf, data, strings);
        helper.writeArray(buffer, overworldGenRules.getHillsTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.getMutateTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.getRiverTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.getShoreTransformations(), writeWeight);
        TriConsumer<ByteBuf, BedrockCodecHelper, BiomeConditionalTransformationData> writeConditionalTransformation =
                (buf, aHelper, data) -> this.writeConditionalTransformation(buf, aHelper, data, strings);
        helper.writeArray(buffer, overworldGenRules.getPreHillsEdgeTransformations(), writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.getPostShoreTransformations(), writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.getClimateTransformations(), this::writeWeightedTemperature);
    }

    protected BiomeOverworldGenRulesData readOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiFunction<ByteBuf, BedrockCodecHelper, BiomeWeightedData> readWeight =
                (buf, aHelper) -> this.readWeight(buf, aHelper, strings);
        List<BiomeWeightedData> hillsTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, hillsTransformations, readWeight);
        List<BiomeWeightedData> mutateTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, mutateTransformations, readWeight);
        List<BiomeWeightedData> riverTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, riverTransformations, readWeight);
        List<BiomeWeightedData> shoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, shoreTransformations, readWeight);
        BiFunction<ByteBuf, BedrockCodecHelper, BiomeConditionalTransformationData> readConditionalTransformation =
                (buf, aHelper) -> this.readConditionalTransformation(buf, aHelper, strings);
        List<BiomeConditionalTransformationData> preHillsEdgeTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, preHillsEdgeTransformations, readConditionalTransformation);
        List<BiomeConditionalTransformationData> postShoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, postShoreTransformations, readConditionalTransformation);
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

    protected void writeWeight(ByteBuf buffer, BiomeWeightedData weightedData, SequencedHashSet<String> strings) {
        buffer.writeShortLE(strings.addAndGetIndex(weightedData.getBiome()));
        buffer.writeIntLE(weightedData.getWeight());
    }

    protected BiomeWeightedData readWeight(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Indexed<String> biome = new Indexed<>(strings, buffer.readShortLE());
        int weight = buffer.readIntLE();
        return new BiomeWeightedData(biome, weight);
    }

    protected void writeConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper,
                                                  BiomeConditionalTransformationData conditionalTransformation,
                                                  SequencedHashSet<String> strings) {
        helper.writeArray(buffer, conditionalTransformation.getWeightedBiomes(),
                (buf, data) -> writeWeight(buf, data, strings));
        buffer.writeShortLE(strings.addAndGetIndex(conditionalTransformation.getConditionJson()));
        buffer.writeIntLE((int) conditionalTransformation.getMinPassingNeighbors());
    }

    protected BiomeConditionalTransformationData readConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper,
                                                                               List<String> strings) {
        List<BiomeWeightedData> weightedBiomes = new ObjectArrayList<>();
        helper.readArray(buffer, weightedBiomes, (buf, aHelper) -> readWeight(buf, aHelper, strings));
        Indexed<String> conditionJson = new Indexed<>(strings, buffer.readShortLE());
        long minPassingNeighbors = buffer.readUnsignedIntLE();
        return new BiomeConditionalTransformationData(weightedBiomes, conditionJson, minPassingNeighbors);
    }

    protected void writeWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper, BiomeWeightedTemperatureData weightedTemperature) {
        VarInts.writeInt(buffer, weightedTemperature.getTemperature().ordinal());
        buffer.writeIntLE((int) weightedTemperature.getWeight());
    }

    protected BiomeWeightedTemperatureData readWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeTemperatureCategory temperature = TEMPERATURE_CATEGORIES[VarInts.readInt(buffer)];
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

    protected void writeLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeLegacyWorldGenRulesData legacyWorldGenRules, SequencedHashSet<String> strings) {
        helper.writeArray(buffer, legacyWorldGenRules.getLegacyPreHills(),
                (buf, aHelper, data) -> this.writeConditionalTransformation(buf, aHelper, data, strings));
    }

    protected BiomeLegacyWorldGenRulesData readLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper,
                                                                   List<String> strings) {
        List<BiomeConditionalTransformationData> legacyPreHills = new ObjectArrayList<>();
        helper.readArray(buffer, legacyPreHills, (buf, aHelper) -> this.readConditionalTransformation(buf, aHelper, strings));
        return new BiomeLegacyWorldGenRulesData(legacyPreHills);
    }

    protected void writeBlock(ByteBuf buffer, BedrockCodecHelper helper, BlockDefinition blockDefinition) {
        if (blockDefinition == null) {
            buffer.writeIntLE(-1);
            return;
        }
        DefinitionUtils.checkDefinition(helper.getBlockDefinitions(), blockDefinition);
        buffer.writeIntLE(blockDefinition.getRuntimeId());
    }

    protected BlockDefinition readBlock(ByteBuf buffer, BedrockCodecHelper helper) {
        int runtimeId = buffer.readIntLE();
        if (runtimeId == -1) {
            return null;
        }
        return helper.getBlockDefinitions().getDefinition(runtimeId);
    }

    protected ExpressionOp readExpressionOp(ByteBuf buffer) {
        int index = VarInts.readInt(buffer);
        if (index == -1) {
            return null;
        }
        return EXPRESSION_OPS[index];
    }

    protected void writeExpressionOp(ByteBuf buffer, ExpressionOp expressionOp) {
        if (expressionOp == null) {
            VarInts.writeInt(buffer, -1);
            return;
        }
        VarInts.writeInt(buffer, expressionOp.ordinal());
    }
}
