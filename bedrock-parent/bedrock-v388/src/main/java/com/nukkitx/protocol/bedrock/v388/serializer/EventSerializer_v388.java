package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.event.*;
import com.nukkitx.protocol.bedrock.v354.serializer.EventSerializer_v354;
import io.netty.buffer.ByteBuf;

import static com.nukkitx.protocol.bedrock.data.event.EventDataType.*;

public class EventSerializer_v388 extends EventSerializer_v354 {
    public static final EventSerializer_v388 INSTANCE = new EventSerializer_v388();

    protected EventSerializer_v388() {
        super();
        this.readers.put(ENTITY_DEFINITION_TRIGGER, this::readEntityDefinitionTrigger);
        this.readers.put(RAID_UPDATE, this::readRaidUpdate);
        this.readers.put(MOVEMENT_ANOMALY, this::readMovementAnomaly);
        this.readers.put(MOVEMENT_CORRECTED, this::readMovementCorrected);
        this.writers.put(ENTITY_DEFINITION_TRIGGER, this::writeEntityDefinitionTrigger);
        this.writers.put(RAID_UPDATE, this::writeRaidUpdate);
        this.writers.put(MOVEMENT_ANOMALY, this::writeMovementAnomaly);
        this.writers.put(MOVEMENT_CORRECTED, this::writeMovementCorrected);
    }

    @Override
    protected MobKilledEventData readMobKilled(ByteBuf buffer, BedrockPacketHelper helper) {
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long victimUniqueEntityId = VarInts.readLong(buffer);
        int killerEntityType = VarInts.readInt(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        int villagerTradeTier = VarInts.readInt(buffer);
        String villagerDisplayName = helper.readString(buffer);
        return new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, killerEntityType, entityDamageCause,
                villagerTradeTier, villagerDisplayName);
    }

    @Override
    protected void writeMobKilled(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        MobKilledEventData event = (MobKilledEventData) eventData;
        VarInts.writeLong(buffer, event.getKillerUniqueEntityId());
        VarInts.writeLong(buffer, event.getVictimUniqueEntityId());
        VarInts.writeInt(buffer, event.getKillerEntityType());
        VarInts.writeInt(buffer, event.getEntityDamageCause());
        VarInts.writeInt(buffer, event.getVillagerTradeTier());
        helper.writeString(buffer, event.getVillagerDisplayName());
    }

    protected EntityDefinitionTriggerEventData readEntityDefinitionTrigger(ByteBuf buffer, BedrockPacketHelper helper) {
        String eventName = helper.readString(buffer);
        return new EntityDefinitionTriggerEventData(eventName);
    }

    protected void writeEntityDefinitionTrigger(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        EntityDefinitionTriggerEventData event = (EntityDefinitionTriggerEventData) eventData;
        helper.writeString(buffer, event.getEventName());
    }

    protected RaidUpdateEventData readRaidUpdate(ByteBuf buffer, BedrockPacketHelper helper) {
        int currentRaidWave = VarInts.readInt(buffer);
        int totalRaidWaves = VarInts.readInt(buffer);
        boolean wonRaid = buffer.readBoolean();
        return new RaidUpdateEventData(currentRaidWave, totalRaidWaves, wonRaid);
    }

    protected void writeRaidUpdate(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        RaidUpdateEventData event = (RaidUpdateEventData) eventData;
        VarInts.writeInt(buffer, event.getCurrentWave());
        VarInts.writeInt(buffer, event.getTotalWaves());
        buffer.writeBoolean(event.isWinner());
    }

    protected MovementAnomalyEventData readMovementAnomaly(ByteBuf buffer, BedrockPacketHelper helper) {
        byte eventType = buffer.readByte();
        float cheatingScore = buffer.readFloatLE();
        float averagePositionDelta = buffer.readFloatLE();
        float totalPositionDelta = buffer.readFloatLE();
        float minPositionDelta = buffer.readFloatLE();
        float maxPositionDelta = buffer.readFloatLE();
        return new MovementAnomalyEventData(eventType, cheatingScore, averagePositionDelta, totalPositionDelta,
                minPositionDelta, maxPositionDelta);
    }

    protected void writeMovementAnomaly(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        MovementAnomalyEventData event = (MovementAnomalyEventData) eventData;
        buffer.writeByte(event.getEventType());
        buffer.writeFloatLE(event.getCheatingScore());
        buffer.writeFloatLE(event.getAveragePositionDelta());
        buffer.writeFloatLE(event.getTotalPositionDelta());
        buffer.writeFloatLE(event.getMinPositionDelta());
        buffer.writeFloatLE(event.getMaxPositionDelta());
    }

    protected MovementCorrectedEventData readMovementCorrected(ByteBuf buffer, BedrockPacketHelper helper) {
        float positionDelta = buffer.readFloatLE();
        float cheatingScore = buffer.readFloatLE();
        float scoreThreshold = buffer.readFloatLE();
        float distanceThreshold = buffer.readFloatLE();
        int durationThreshold = VarInts.readInt(buffer);
        return new MovementCorrectedEventData(positionDelta, cheatingScore, scoreThreshold, distanceThreshold,
                durationThreshold);
    }

    protected void writeMovementCorrected(ByteBuf buffer, BedrockPacketHelper helper, EventData eventData) {
        MovementCorrectedEventData event = (MovementCorrectedEventData) eventData;
        buffer.writeFloatLE(event.getPositionDelta());
        buffer.writeFloatLE(event.getCheatingScore());
        buffer.writeFloatLE(event.getScoreThreshold());
        buffer.writeFloatLE(event.getDistanceThreshold());
        VarInts.writeInt(buffer, event.getDurationThreshold());
    }
}
