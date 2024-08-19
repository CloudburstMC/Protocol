package org.cloudburstmc.protocol.bedrock.codec.v471.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v389.serializer.EventSerializer_v389;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.event.*;
import org.cloudburstmc.protocol.common.util.DefinitionUtils;
import org.cloudburstmc.protocol.common.util.VarInts;

public class EventSerializer_v471 extends EventSerializer_v389 {
    public static final EventSerializer_v471 INSTANCE = new EventSerializer_v471();

    protected EventSerializer_v471() {
        super();
        this.readers.put(EventDataType.TARGET_BLOCK_HIT, this::readBlockHit);
        this.writers.put(EventDataType.TARGET_BLOCK_HIT, this::writeBlockHit);
        this.readers.put(EventDataType.PIGLIN_BARTER, this::readPiglinBarter);
        this.writers.put(EventDataType.PIGLIN_BARTER, this::writePiglinBarter);
        this.readers.put(EventDataType.COPPER_WAXED_OR_UNWAXED, this::readCopperWaxedUnwaxed);
        this.writers.put(EventDataType.COPPER_WAXED_OR_UNWAXED, this::writeCopperWaxedUnwaxed);
        this.readers.put(EventDataType.CODE_BUILDER_ACTION, this::readCodeBuilderAction);
        this.writers.put(EventDataType.CODE_BUILDER_ACTION, this::writeCodeBuilderAction);
        this.readers.put(EventDataType.CODE_BUILDER_SCOREBOARD, this::readCodeBuilderScoreboard);
        this.writers.put(EventDataType.CODE_BUILDER_SCOREBOARD, this::writeCodeBuilderScoreboard);
        this.readers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h) -> StriderRiddenInLavaInOverworldEventData.INSTANCE);
        this.writers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h, e) -> {});
        this.readers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h) -> SneakCloseToSculkSensorEventData.INSTANCE);
        this.writers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h, e) -> {});
    }

    protected TargetBlockHitEventData readBlockHit(ByteBuf buffer, BedrockCodecHelper helper) {
        int redstoneLevel = VarInts.readInt(buffer);
        return new TargetBlockHitEventData(redstoneLevel);
    }

    protected void writeBlockHit(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        TargetBlockHitEventData event = (TargetBlockHitEventData) eventData;
        VarInts.writeInt(buffer, event.getRedstoneLevel());
    }

    protected PiglinBarterEventData readPiglinBarter(ByteBuf buffer, BedrockCodecHelper helper) {
        int runtimeId = VarInts.readInt(buffer);
        ItemDefinition itemDefinition = helper.getItemDefinitions().getDefinition(runtimeId);
        boolean targetingPlayer = buffer.readBoolean();
        return new PiglinBarterEventData(itemDefinition, targetingPlayer);
    }

    protected void writePiglinBarter(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PiglinBarterEventData event = (PiglinBarterEventData) eventData;
        VarInts.writeInt(buffer, event.getDefinition().getRuntimeId());
        buffer.writeBoolean(event.isTargetingPlayer());
    }

    protected CopperWaxedOrUnwaxedEventData readCopperWaxedUnwaxed(ByteBuf buffer, BedrockCodecHelper helper) {
        int runtimeId = VarInts.readInt(buffer);
        BlockDefinition blockDefinition = helper.getBlockDefinitions().getDefinition(runtimeId);
        return new CopperWaxedOrUnwaxedEventData(blockDefinition);
    }

    protected void writeCopperWaxedUnwaxed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CopperWaxedOrUnwaxedEventData event = (CopperWaxedOrUnwaxedEventData) eventData;
        VarInts.writeInt(buffer, DefinitionUtils.checkDefinition(helper.getBlockDefinitions(), event.getDefinition()).getRuntimeId());
    }

    protected CodeBuilderActionEventData readCodeBuilderAction(ByteBuf buffer, BedrockCodecHelper helper) {
        String action = helper.readString(buffer);
        return new CodeBuilderActionEventData(action);
    }

    protected void writeCodeBuilderAction(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CodeBuilderActionEventData event = (CodeBuilderActionEventData) eventData;
        helper.writeString(buffer, event.getAction());
    }

    protected CodeBuilderScoreboardEventData readCodeBuilderScoreboard(ByteBuf buffer, BedrockCodecHelper helper) {
        String objectiveName = helper.readString(buffer);
        int score = VarInts.readInt(buffer);
        return new CodeBuilderScoreboardEventData(objectiveName, score);
    }

    protected void writeCodeBuilderScoreboard(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CodeBuilderScoreboardEventData event = (CodeBuilderScoreboardEventData) eventData;
        helper.writeString(buffer, event.getObjectiveName());
        VarInts.writeInt(buffer, event.getScore());
    }
}
