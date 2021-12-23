package com.nukkitx.protocol.bedrock.v471.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.event.CodeBuilderActionEventData;
import com.nukkitx.protocol.bedrock.data.event.CodeBuilderScoreboardEventData;
import com.nukkitx.protocol.bedrock.data.event.CopperWaxedOrUnwaxedEventData;
import com.nukkitx.protocol.bedrock.data.event.EventData;
import com.nukkitx.protocol.bedrock.data.event.EventDataType;
import com.nukkitx.protocol.bedrock.data.event.PiglinBarterEventData;
import com.nukkitx.protocol.bedrock.data.event.SneakCloseToSculkSensorEventData;
import com.nukkitx.protocol.bedrock.data.event.StriderRiddenInLavaInOverworldEventData;
import com.nukkitx.protocol.bedrock.data.event.TargetBlockHitEventData;
import com.nukkitx.protocol.bedrock.v389.serializer.EventSerializer_v389;
import io.netty.buffer.ByteBuf;

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
        this.readers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h) -> StriderRiddenInLavaInOverworldEventData.INSTANCE);
        this.writers.put(EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD, (b, h, e) -> {});
        this.readers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h) -> SneakCloseToSculkSensorEventData.INSTANCE);
        this.writers.put(EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR, (b, h, e) -> {});
    }

    protected TargetBlockHitEventData readBlockHit(ByteBuf buffer, BedrockPacketHelper helper) {
        int redstoneLevel = VarInts.readInt(buffer);
        return new TargetBlockHitEventData(redstoneLevel);
    }

    protected void writeBlockHit(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        TargetBlockHitEventData event = (TargetBlockHitEventData) eventData;
        VarInts.writeInt(buffer, event.getRedstoneLevel());
    }

    protected PiglinBarterEventData readPiglinBarter(ByteBuf buffer, BedrockPacketHelper helper) {
        int runtimeId = VarInts.readInt(buffer);
        boolean targetingPlayer = buffer.readBoolean();
        return new PiglinBarterEventData(runtimeId, targetingPlayer);
    }

    protected void writePiglinBarter(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        PiglinBarterEventData event = (PiglinBarterEventData) eventData;
        VarInts.writeInt(buffer, event.getItemId());
        buffer.writeBoolean(event.isTargetingPlayer());
    }

    protected CopperWaxedOrUnwaxedEventData readCopperWaxedUnwaxed(ByteBuf buffer, BedrockPacketHelper helper) {
        int runtimeId = VarInts.readInt(buffer);
        return new CopperWaxedOrUnwaxedEventData(runtimeId);
    }

    protected void writeCopperWaxedUnwaxed(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        CopperWaxedOrUnwaxedEventData event = (CopperWaxedOrUnwaxedEventData) eventData;
        VarInts.writeInt(buffer, event.getBlockRuntimeId());
    }

    protected CodeBuilderActionEventData readCodeBuilderAction(ByteBuf buffer, BedrockPacketHelper helper) {
        String action = helper.readString(buffer);
        return new CodeBuilderActionEventData(action);
    }

    protected void writeCodeBuilderAction(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        CodeBuilderActionEventData event = (CodeBuilderActionEventData) eventData;
        helper.writeString(buffer, event.getAction());
    }

    protected CodeBuilderScoreboardEventData readCodeBuilderScoreboard(ByteBuf buffer, BedrockPacketHelper helper) {
        String objectiveName = helper.readString(buffer);
        int score = VarInts.readInt(buffer);
        return new CodeBuilderScoreboardEventData(objectiveName, score);
    }

    protected void writeCodeBuilderScoreboard(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        CodeBuilderScoreboardEventData event = (CodeBuilderScoreboardEventData) eventData;
        helper.writeString(buffer, event.getObjectiveName());
        VarInts.writeInt(buffer, event.getScore());
    }
}
