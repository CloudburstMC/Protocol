package com.nukkitx.protocol.bedrock.v402.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.ListTag;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StartGameSerializer_v402 implements PacketSerializer<StartGamePacket> {

    public static final StartGameSerializer_v402 INSTANCE = new StartGameSerializer_v402();

    private static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

    @Override
    public void serialize(ByteBuf buffer, StartGamePacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getPlayerGamemode());
        BedrockUtils.writeVector3f(buffer, packet.getPlayerPosition());
        BedrockUtils.writeVector2f(buffer, packet.getRotation());
        // Level settings start
        VarInts.writeInt(buffer, packet.getSeed());
        VarInts.writeInt(buffer, packet.getDimensionId());
        VarInts.writeInt(buffer, packet.getGeneratorId());
        VarInts.writeInt(buffer, packet.getLevelGamemode());
        VarInts.writeInt(buffer, packet.getDifficulty());
        BedrockUtils.writeBlockPosition(buffer, packet.getDefaultSpawn());
        buffer.writeBoolean(packet.isAchievementsDisabled());
        VarInts.writeInt(buffer, packet.getTime());
        VarInts.writeInt(buffer, packet.getEduEditionOffers());
        buffer.writeBoolean(packet.isEduFeaturesEnabled());
        BedrockUtils.writeString(buffer, packet.getUnknownString0());
        buffer.writeFloatLE(packet.getRainLevel());
        buffer.writeFloatLE(packet.getLightningLevel());
        buffer.writeBoolean(packet.isPlatformLockedContentConfirmed());
        buffer.writeBoolean(packet.isMultiplayerGame());
        buffer.writeBoolean(packet.isBroadcastingToLan());
        VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
        VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
        buffer.writeBoolean(packet.isCommandsEnabled());
        buffer.writeBoolean(packet.isTexturePacksRequired());
        BedrockUtils.writeArray(buffer, packet.getGamerules(), BedrockUtils::writeGameRule);
        buffer.writeBoolean(packet.isBonusChestEnabled());
        buffer.writeBoolean(packet.isStartingWithMap());
        VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
        buffer.writeIntLE(packet.getServerChunkTickRange());
        buffer.writeBoolean(packet.isBehaviorPackLocked());
        buffer.writeBoolean(packet.isResourcePackLocked());
        buffer.writeBoolean(packet.isFromLockedWorldTemplate());
        buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
        buffer.writeBoolean(packet.isFromWorldTemplate());
        buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
        buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
        BedrockUtils.writeString(buffer, packet.getVanillaVersion());
        buffer.writeIntLE(packet.getUnknownInt0());
        buffer.writeIntLE(packet.getUnknownInt1());

        // Level settings end
        BedrockUtils.writeString(buffer, packet.getLevelId());
        BedrockUtils.writeString(buffer, packet.getWorldName());
        BedrockUtils.writeString(buffer, packet.getPremiumWorldTemplateId());
        buffer.writeBoolean(packet.isTrial());
        buffer.writeBoolean(packet.isMovementServerAuthoritative());
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        // cache palette for fast writing
        try (NBTOutputStream stream = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            stream.write(packet.getBlockPalette());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BedrockUtils.writeArray(buffer, packet.getItemEntries(), (buf, entry) -> {
            BedrockUtils.writeString(buf, entry.getIdentifier());
            buf.writeShortLE(entry.getId());
        });

        BedrockUtils.writeString(buffer, packet.getMultiplayerCorrelationId());
        buffer.writeBoolean(packet.isUnknownBool0());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deserialize(ByteBuf buffer, StartGamePacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlayerGamemode(VarInts.readInt(buffer));
        packet.setPlayerPosition(BedrockUtils.readVector3f(buffer));
        packet.setRotation(BedrockUtils.readVector2f(buffer));
        // Level settings start
        packet.setSeed(VarInts.readInt(buffer));
        packet.setDimensionId(VarInts.readInt(buffer));
        packet.setGeneratorId(VarInts.readInt(buffer));
        packet.setLevelGamemode(VarInts.readInt(buffer));
        packet.setDifficulty(VarInts.readInt(buffer));
        packet.setDefaultSpawn(BedrockUtils.readBlockPosition(buffer));
        packet.setAchievementsDisabled(buffer.readBoolean());
        packet.setTime(VarInts.readInt(buffer));
        packet.setEduEditionOffers(VarInts.readInt(buffer));
        packet.setEduFeaturesEnabled(buffer.readBoolean());
        packet.setUnknownString0(BedrockUtils.readString(buffer));
        packet.setRainLevel(buffer.readFloatLE());
        packet.setLightningLevel(buffer.readFloatLE());
        packet.setPlatformLockedContentConfirmed(buffer.readBoolean());
        packet.setMultiplayerGame(buffer.readBoolean());
        packet.setBroadcastingToLan(buffer.readBoolean());
        packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        packet.setCommandsEnabled(buffer.readBoolean());
        packet.setTexturePacksRequired(buffer.readBoolean());
        BedrockUtils.readArray(buffer, packet.getGamerules(), BedrockUtils::readGameRule);
        packet.setBonusChestEnabled(buffer.readBoolean());
        packet.setStartingWithMap(buffer.readBoolean());
        packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
        packet.setServerChunkTickRange(buffer.readIntLE());
        packet.setBehaviorPackLocked(buffer.readBoolean());
        packet.setResourcePackLocked(buffer.readBoolean());
        packet.setFromLockedWorldTemplate(buffer.readBoolean());
        packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
        packet.setFromWorldTemplate(buffer.readBoolean());
        packet.setWorldTemplateOptionLocked(buffer.readBoolean());
        packet.setOnlySpawningV1Villagers(buffer.readBoolean());
        packet.setVanillaVersion(BedrockUtils.readString(buffer));
        packet.setUnknownInt0(buffer.readIntLE());
        packet.setUnknownInt1(buffer.readIntLE());
        // Level settings end
        packet.setLevelId(BedrockUtils.readString(buffer));
        packet.setWorldName(BedrockUtils.readString(buffer));
        packet.setPremiumWorldTemplateId(BedrockUtils.readString(buffer));
        packet.setTrial(buffer.readBoolean());
        packet.setMovementServerAuthoritative(buffer.readBoolean());
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        try (NBTInputStream stream = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setBlockPalette((ListTag<CompoundTag>) stream.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BedrockUtils.readArray(buffer, packet.getItemEntries(), buf -> {
            String identifier = BedrockUtils.readString(buf);
            short id = buf.readShortLE();
            return new StartGamePacket.ItemEntry(identifier, id);
        });

        packet.setMultiplayerCorrelationId(BedrockUtils.readString(buffer));
        packet.setUnknownBool0(buffer.readBoolean());
    }
}