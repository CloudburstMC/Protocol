package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;


public class StartGamePacket_v291 extends StartGamePacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        VarInts.writeInt(buffer, playerGamemode);
        BedrockUtils.writeVector3f(buffer, playerPosition);
        BedrockUtils.writeVector2f(buffer, rotation);
        // Level settings start
        VarInts.writeInt(buffer, seed);
        VarInts.writeInt(buffer, dimensionId);
        VarInts.writeInt(buffer, generatorId);
        VarInts.writeInt(buffer, levelGamemode);
        VarInts.writeInt(buffer, difficulty);
        BedrockUtils.writeBlockPosition(buffer, defaultSpawn);
        buffer.writeBoolean(acheivementsDisabled);
        VarInts.writeInt(buffer, time);
        buffer.writeBoolean(eduLevel);
        buffer.writeBoolean(eduFeaturesEnabled);
        buffer.writeFloatLE(rainLevel);
        buffer.writeFloatLE(lightningLevel);
        buffer.writeBoolean(multiplayerGame);
        buffer.writeBoolean(broadcastingToLan);
        buffer.writeBoolean(broadcastingToXbl);
        buffer.writeBoolean(commandsEnabled);
        buffer.writeBoolean(texturePacksRequired);
        BedrockUtils.writeArray(buffer, gamerules, BedrockUtils::writeGameRule);
        buffer.writeBoolean(bonusChestEnabled);
        buffer.writeBoolean(startingWithMap);
        buffer.writeBoolean(trustingPlayers);
        VarInts.writeInt(buffer, defaultPlayerPermission);
        VarInts.writeInt(buffer, xblBroadcastMode);
        buffer.writeIntLE(serverChunkTickRange);
        buffer.writeBoolean(broadcastingToPlatform);
        VarInts.writeInt(buffer, platformBroadcastMode);
        buffer.writeBoolean(intentOnXblBroadcast);
        buffer.writeBoolean(behaviorPackLocked);
        buffer.writeBoolean(resourcePackLocked);
        buffer.writeBoolean(fromLockedWorldTemplate);
        buffer.writeBoolean(usingMsaGamertagsOnly);
        // Level settings end
        BedrockUtils.writeString(buffer, levelId);
        BedrockUtils.writeString(buffer, worldName);
        BedrockUtils.writeString(buffer, premiumWorldTemplateId);
        buffer.writeBoolean(trial);
        buffer.writeLongLE(currentTick);
        VarInts.writeInt(buffer, enchantmentSeed);

        // cache palette for fast writing
        if (cachedPalette != null) {
            buffer.writeBytes(cachedPalette);
            cachedPalette.release();
        } else {
            VarInts.writeUnsignedInt(buffer, paletteEntries.size());
            for (PaletteEntry entry : paletteEntries) {
                BedrockUtils.writeString(buffer, entry.getIdentifier());
                buffer.writeShortLE(entry.getMeta());
            }
        }

        BedrockUtils.writeString(buffer, multiplayerCorrelationId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        playerGamemode = VarInts.readInt(buffer);
        playerPosition = BedrockUtils.readVector3f(buffer);
        rotation = BedrockUtils.readVector2f(buffer);
        // Level settings start
        seed = VarInts.readInt(buffer);
        dimensionId = VarInts.readInt(buffer);
        generatorId = VarInts.readInt(buffer);
        levelGamemode = VarInts.readInt(buffer);
        difficulty = VarInts.readInt(buffer);
        defaultSpawn = BedrockUtils.readBlockPosition(buffer);
        acheivementsDisabled = buffer.readBoolean();
        time = VarInts.readInt(buffer);
        eduLevel = buffer.readBoolean();
        eduFeaturesEnabled = buffer.readBoolean();
        rainLevel = buffer.readFloatLE();
        lightningLevel = buffer.readFloatLE();
        multiplayerGame = buffer.readBoolean();
        broadcastingToLan = buffer.readBoolean();
        broadcastingToXbl = buffer.readBoolean();
        commandsEnabled = buffer.readBoolean();
        texturePacksRequired = buffer.readBoolean();
        BedrockUtils.readArray(buffer, gamerules, BedrockUtils::readGameRule);
        bonusChestEnabled = buffer.readBoolean();
        startingWithMap = buffer.readBoolean();
        trustingPlayers = buffer.readBoolean();
        defaultPlayerPermission = VarInts.readInt(buffer);
        xblBroadcastMode = VarInts.readInt(buffer);
        serverChunkTickRange = buffer.readIntLE();
        broadcastingToPlatform = buffer.readBoolean();
        platformBroadcastMode = VarInts.readInt(buffer);
        intentOnXblBroadcast = buffer.readBoolean();
        behaviorPackLocked = buffer.readBoolean();
        resourcePackLocked = buffer.readBoolean();
        fromLockedWorldTemplate = buffer.readBoolean();
        usingMsaGamertagsOnly = buffer.readBoolean();
        // Level settings end
        levelId = BedrockUtils.readString(buffer);
        worldName = BedrockUtils.readString(buffer);
        premiumWorldTemplateId = BedrockUtils.readString(buffer);
        trial = buffer.readBoolean();
        currentTick = buffer.readLongLE();
        enchantmentSeed = VarInts.readInt(buffer);

        BedrockUtils.readArray(buffer, paletteEntries, buf -> {
            String identifier = BedrockUtils.readString(buffer);
            short meta = buffer.readShortLE();
            return new PaletteEntry(identifier, meta);
        });

        multiplayerCorrelationId = BedrockUtils.readString(buffer);
    }
}
