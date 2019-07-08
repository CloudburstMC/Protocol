package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.event.*;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventSerializer_v354 implements PacketSerializer<EventPacket> {
    public static final EventSerializer_v354 INSTANCE = new EventSerializer_v354();

    private static final EventDataType[] VALUES = EventDataType.values();

    @Override
    public void serialize(ByteBuf buffer, EventPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        EventData eventData = packet.getEventData();
        VarInts.writeInt(buffer, eventData.getType().ordinal());
        buffer.writeByte(packet.getUnknown0());

        switch (eventData.getType()) {
            case ACHIEVEMENT_AWARDED:
                VarInts.writeInt(buffer, ((AchievementAwardedEventData) eventData).getAchievementId());
                break;
            case ENTITY_INTERACT:
                EntityInteractEventData entityInteractEventData = (EntityInteractEventData) eventData;
                VarInts.writeInt(buffer, entityInteractEventData.getInteractionType());
                VarInts.writeInt(buffer, entityInteractEventData.getLegacyEntityTypeId());
                VarInts.writeInt(buffer, entityInteractEventData.getVariant());
                buffer.writeShortLE(entityInteractEventData.getPaletteColor());
                break;
            case PORTAL_BUILT:
                VarInts.writeInt(buffer, ((PortalBuiltEventData) eventData).getDimensionId());
                break;
            case PORTAL_USED:
                PortalUsedEventData portalUsedEventData = (PortalUsedEventData) eventData;
                VarInts.writeInt(buffer, portalUsedEventData.getFromDimensionId());
                VarInts.writeInt(buffer, portalUsedEventData.getToDimensionId());
                break;
            case MOB_KILLED:
                MobKilledEventData mobKilledEventData = (MobKilledEventData) eventData;
                VarInts.writeLong(buffer, mobKilledEventData.getKillerUniqueEntityId());
                VarInts.writeLong(buffer, mobKilledEventData.getVictimUniqueEntityId());
                VarInts.writeInt(buffer, mobKilledEventData.getEntityDamageCause());
                VarInts.writeInt(buffer, mobKilledEventData.getVillagerTradeTier());
                BedrockUtils.writeString(buffer, mobKilledEventData.getVillagerDisplayName());
                break;
            case CAULDRON_USED:
                CauldronUsedEventData cauldronUsedEventData = (CauldronUsedEventData) eventData;
                VarInts.writeUnsignedInt(buffer, cauldronUsedEventData.getPotionId());
                VarInts.writeInt(buffer, cauldronUsedEventData.getColor());
                VarInts.writeInt(buffer, cauldronUsedEventData.getFillLevel());
                break;
            case PLAYER_DIED:
                PlayerDiedEventData playerDiedEventData = (PlayerDiedEventData) eventData;
                VarInts.writeInt(buffer, playerDiedEventData.getUnknown0());
                VarInts.writeInt(buffer, playerDiedEventData.getUnknown1());
                VarInts.writeInt(buffer, playerDiedEventData.getEntityDamageCause());
                buffer.writeBoolean(playerDiedEventData.isInRaid());
                break;
            case BOSS_KILLED:
                BossKilledEventData bossKilledEventData = (BossKilledEventData) eventData;
                VarInts.writeLong(buffer, bossKilledEventData.getBossUniqueEntityId());
                VarInts.writeInt(buffer, bossKilledEventData.getPlayerPartySize());
                VarInts.writeInt(buffer, bossKilledEventData.getLegacyEntityTypeId());
                break;
            case AGENT_COMMAND:
                AgentCommandEventData agentCommandEventData = (AgentCommandEventData) eventData;
                VarInts.writeInt(buffer, agentCommandEventData.getAgentResult());
                VarInts.writeInt(buffer, agentCommandEventData.getDataValue());
                BedrockUtils.writeString(buffer, agentCommandEventData.getCommand());
                BedrockUtils.writeString(buffer, agentCommandEventData.getDataKey());
                BedrockUtils.writeString(buffer, agentCommandEventData.getOutput());
                break;
            case PATTERN_REMOVED:
                PatternRemovedEventData patternRemovedEventData = (PatternRemovedEventData) eventData;
                VarInts.writeInt(buffer, patternRemovedEventData.getItemId());
                VarInts.writeInt(buffer, patternRemovedEventData.getAuxValue());
                VarInts.writeInt(buffer, patternRemovedEventData.getPatternsSize());
                VarInts.writeInt(buffer, patternRemovedEventData.getPatternIndex());
                VarInts.writeInt(buffer, patternRemovedEventData.getPatternColor());
                break;
            case SLASH_COMMAND_EXECUTED:
                SlashCommandExecutedEventData slashCommandExecutedEventData = (SlashCommandExecutedEventData) eventData;
                VarInts.writeInt(buffer, slashCommandExecutedEventData.getSuccessCount());
                List<String> outputMessages = slashCommandExecutedEventData.getOutputMessages();
                VarInts.writeInt(buffer, outputMessages.size());
                BedrockUtils.writeString(buffer, slashCommandExecutedEventData.getCommandName());
                BedrockUtils.writeString(buffer, String.join(";", outputMessages));
                break;
            case FISH_BUCKETED:
                FishBucketedEventData fishBucketedEventData = (FishBucketedEventData) eventData;
                VarInts.writeInt(buffer, fishBucketedEventData.getUnknown0());
                VarInts.writeInt(buffer, fishBucketedEventData.getUnknown1());
                VarInts.writeInt(buffer, fishBucketedEventData.getUnknown2());
                buffer.writeBoolean(fishBucketedEventData.isUnknown3());
                break;
            case MOB_BORN:
                MobBornEventData mobBornEventData = (MobBornEventData) eventData;
                VarInts.writeInt(buffer, mobBornEventData.getLegacyEntityTypeId());
                VarInts.writeInt(buffer, mobBornEventData.getVariant());
                buffer.writeShortLE(mobBornEventData.getColor());
                break;
            case PET_DIED:
                PetDiedEventData petDiedEventData = (PetDiedEventData) eventData;
                buffer.writeBoolean(petDiedEventData.isUnknown0());
                VarInts.writeLong(buffer, petDiedEventData.getKillerUniqueEntityId());
                VarInts.writeLong(buffer, petDiedEventData.getPetUniqueEntityId());
                VarInts.writeInt(buffer, petDiedEventData.getEntityDamageCause());
                VarInts.writeInt(buffer, petDiedEventData.getUnknown1());
                break;
            case CAULDRON_BLOCK_USED:
                CauldronBlockUsedEventData cauldronBlockUsedEventData = (CauldronBlockUsedEventData) eventData;
                VarInts.writeInt(buffer, cauldronBlockUsedEventData.getBlockInteractionType());
                VarInts.writeInt(buffer, cauldronBlockUsedEventData.getUnknown0());
                break;
            case COMPOSTER_BLOCK_USED:
                ComposterBlockUsedEventData composterBlockUsedEventData = (ComposterBlockUsedEventData) eventData;
                VarInts.writeInt(buffer, composterBlockUsedEventData.getBlockInteractionType());
                VarInts.writeInt(buffer, composterBlockUsedEventData.getUnknown0());
                break;
            case BELL_BLOCK_USED:
                VarInts.writeInt(buffer, ((BellBlockUsedEventData) eventData).getUnknown0());
                break;
            case AGENT_CREATED:
                // No extra data
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));

        int eventId = VarInts.readInt(buffer);
        Preconditions.checkElementIndex(eventId, VALUES.length, "EventDataType");
        EventDataType type = VALUES[eventId];

        packet.setUnknown0(buffer.readByte());

        EventData data;

        switch (type) {
            case ACHIEVEMENT_AWARDED:
                data = new AchievementAwardedEventData(VarInts.readInt(buffer));
                break;
            case ENTITY_INTERACT:
                int interactionType = VarInts.readInt(buffer);
                int legacyEntityTypeId = VarInts.readInt(buffer);
                int variant = VarInts.readInt(buffer);
                int paletteColor = buffer.readUnsignedShortLE();
                data = new EntityInteractEventData(interactionType, legacyEntityTypeId, variant, paletteColor);
                break;
            case PORTAL_BUILT:
                data = new PortalBuiltEventData(VarInts.readInt(buffer));
                break;
            case PORTAL_USED:
                int fromDimensionId = VarInts.readInt(buffer);
                int toDimensionId = VarInts.readInt(buffer);
                data = new PortalUsedEventData(fromDimensionId, toDimensionId);
                break;
            case MOB_KILLED:
                long killerUniqueEntityId = VarInts.readLong(buffer);
                long victimUniqueEntityId = VarInts.readLong(buffer);
                int entityDamageCause = VarInts.readInt(buffer);
                int villagerTradeTier = VarInts.readInt(buffer);
                String villagerDisplayName = BedrockUtils.readString(buffer);
                data = new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, entityDamageCause,
                        villagerTradeTier, villagerDisplayName);
                break;
            case CAULDRON_USED:
                int potionId = VarInts.readInt(buffer);
                int color = VarInts.readInt(buffer);
                int fillLevel = VarInts.readInt(buffer);
                data = new CauldronUsedEventData(potionId, color, fillLevel);
                break;
            case PLAYER_DIED:
                int unknown0 = VarInts.readInt(buffer);
                int unknown1 = VarInts.readInt(buffer);
                entityDamageCause = VarInts.readInt(buffer);
                boolean inRaid = buffer.readBoolean();
                data = new PlayerDiedEventData(unknown0, unknown1, entityDamageCause, inRaid);
                break;
            case BOSS_KILLED:
                long bossUniqueEntityId = VarInts.readLong(buffer);
                int playerPartySize = VarInts.readInt(buffer);
                legacyEntityTypeId = VarInts.readInt(buffer);
                data = new BossKilledEventData(bossUniqueEntityId, playerPartySize, legacyEntityTypeId);
                break;
            case AGENT_COMMAND:
                int agentResult = VarInts.readInt(buffer);
                int dataValue = VarInts.readInt(buffer);
                String command = BedrockUtils.readString(buffer);
                String dataKey = BedrockUtils.readString(buffer);
                String output = BedrockUtils.readString(buffer);
                data = new AgentCommandEventData(agentResult, command, dataKey, dataValue, output);
                break;
            case AGENT_CREATED:
                data = AgentCreatedEventData.INSTANCE;
                break;
            case PATTERN_REMOVED:
                int itemId = VarInts.readInt(buffer);
                int auxValue = VarInts.readInt(buffer);
                int patternsSize = VarInts.readInt(buffer);
                int patternIndex = VarInts.readInt(buffer);
                int patternColor = VarInts.readInt(buffer);
                data = new PatternRemovedEventData(itemId, auxValue, patternsSize, patternIndex, patternColor);
                break;
            case SLASH_COMMAND_EXECUTED:
                int successCount = VarInts.readInt(buffer);
                VarInts.readInt(buffer);
                String commandName = BedrockUtils.readString(buffer);
                List<String> outputMessages = Arrays.asList(BedrockUtils.readString(buffer).split(";"));
                data = new SlashCommandExecutedEventData(commandName, successCount, outputMessages);
                break;
            case FISH_BUCKETED:
                unknown0 = VarInts.readInt(buffer);
                unknown1 = VarInts.readInt(buffer);
                int unknown2 = VarInts.readInt(buffer);
                boolean unknown3 = buffer.readBoolean();
                data = new FishBucketedEventData(unknown0, unknown1, unknown2, unknown3);
                break;
            case MOB_BORN:
                legacyEntityTypeId = VarInts.readInt(buffer);
                variant = VarInts.readInt(buffer);
                color = buffer.readUnsignedShortLE();
                data = new MobBornEventData(legacyEntityTypeId, variant, color);
                break;
            case PET_DIED:
                boolean unknownBool = buffer.readBoolean();
                killerUniqueEntityId = VarInts.readLong(buffer);
                long petUniqueEntityId = VarInts.readLong(buffer);
                entityDamageCause = VarInts.readInt(buffer);
                unknown1 = VarInts.readInt(buffer);
                data = new PetDiedEventData(unknownBool, killerUniqueEntityId, petUniqueEntityId, entityDamageCause,
                        unknown1);
                break;
            case CAULDRON_BLOCK_USED:
                int blockInterationType = VarInts.readInt(buffer);
                unknown0 = VarInts.readInt(buffer);
                data = new CauldronBlockUsedEventData(blockInterationType, unknown0);
                break;
            case COMPOSTER_BLOCK_USED:
                blockInterationType = VarInts.readInt(buffer);
                unknown0 = VarInts.readInt(buffer);
                data = new ComposterBlockUsedEventData(blockInterationType, unknown0);
                break;
            case BELL_BLOCK_USED:
                data = new BellBlockUsedEventData(VarInts.readInt(buffer));
                break;
            default:
                throw new IllegalArgumentException("Unknown EventDataType");
        }
        packet.setEventData(data);
    }
}
