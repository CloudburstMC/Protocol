package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtList;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.GamePublishSetting;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v291 implements BedrockPacketSerializer<StartGamePacket> {
    public static final StartGameSerializer_v291 INSTANCE = new StartGameSerializer_v291();

    protected static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector2f(buffer, packet.getRotation());

        this.writeLevelSettings(buffer, helper, packet);

        helper.writeString(buffer, packet.getLevelId());
        helper.writeString(buffer, packet.getLevelName());
        helper.writeString(buffer, packet.getPremiumWorldTemplateId());
        buffer.writeBoolean(packet.isTrial());
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        NbtList<NbtMap> palette = packet.getBlockPalette();
        VarInts.writeUnsignedInt(buffer, palette.size());
        for (NbtMap entry : palette) {
            NbtMap blockTag = entry.getCompound("block");
            helper.writeString(buffer, blockTag.getString("name"));
            buffer.writeShortLE(entry.getShort("meta"));
        }

        helper.writeString(buffer, packet.getMultiplayerCorrelationId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector2f(buffer));

        this.readLevelSettings(buffer, helper, packet);

        packet.setLevelId(helper.readString(buffer));
        packet.setLevelName(helper.readString(buffer));
        packet.setPremiumWorldTemplateId(helper.readString(buffer));
        packet.setTrial(buffer.readBoolean());
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        int paletteLength = VarInts.readUnsignedInt(buffer);
        List<NbtMap> palette = new ObjectArrayList<>(paletteLength);
        for (int i = 0; i < paletteLength; i++) {
            palette.add(NbtMap.builder()
                    .putCompound("block", NbtMap.builder()
                            .putString("name", helper.readString(buffer))
                            .build())
                    .putShort("meta", buffer.readShortLE())
                    .build());
        }
        packet.setBlockPalette(new NbtList<>(NbtType.COMPOUND, palette));

        packet.setMultiplayerCorrelationId(helper.readString(buffer));
    }

    protected void writeLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        writeSeed(buffer, packet.getSeed());
        VarInts.writeInt(buffer, packet.getDimensionId());
        VarInts.writeInt(buffer, packet.getGeneratorId());
        VarInts.writeInt(buffer, packet.getLevelGameType().ordinal());
        VarInts.writeInt(buffer, packet.getDifficulty());
        helper.writeBlockPosition(buffer, packet.getDefaultSpawn());
        buffer.writeBoolean(packet.isAchievementsDisabled());
        VarInts.writeInt(buffer, packet.getDayCycleStopTime());
        buffer.writeBoolean(packet.getEduEditionOffers() != 0); // Is Education world
        buffer.writeBoolean(packet.isEduFeaturesEnabled());
        buffer.writeFloatLE(packet.getRainLevel());
        buffer.writeFloatLE(packet.getLightningLevel());
        buffer.writeBoolean(packet.isMultiplayerGame());
        buffer.writeBoolean(packet.isBroadcastingToLan());
        buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        buffer.writeBoolean(packet.isCommandsEnabled());
        buffer.writeBoolean(packet.isTexturePacksRequired());
        helper.writeArray(buffer, packet.getGamerules(), helper::writeGameRule);
        buffer.writeBoolean(packet.isBonusChestEnabled());
        buffer.writeBoolean(packet.isStartingWithMap());
        buffer.writeBoolean(packet.isTrustingPlayers());
        VarInts.writeInt(buffer, packet.getDefaultPlayerPermission().ordinal());
        VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
        buffer.writeIntLE(packet.getServerChunkTickRange());
        buffer.writeBoolean(packet.getPlatformBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
        buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        buffer.writeBoolean(packet.isBehaviorPackLocked());
        buffer.writeBoolean(packet.isResourcePackLocked());
        buffer.writeBoolean(packet.isFromLockedWorldTemplate());
        buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
    }

    protected void readLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        packet.setSeed(readSeed(buffer));
        packet.setDimensionId(VarInts.readInt(buffer));
        packet.setGeneratorId(VarInts.readInt(buffer));
        packet.setLevelGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setDifficulty(VarInts.readInt(buffer));
        packet.setDefaultSpawn(helper.readBlockPosition(buffer));
        packet.setAchievementsDisabled(buffer.readBoolean());
        packet.setDayCycleStopTime(VarInts.readInt(buffer));
        packet.setEduEditionOffers(buffer.readBoolean() ? 1 : 0); // Is Education world
        packet.setEduFeaturesEnabled(buffer.readBoolean());
        packet.setRainLevel(buffer.readFloatLE());
        packet.setLightningLevel(buffer.readFloatLE());
        packet.setMultiplayerGame(buffer.readBoolean());
        packet.setBroadcastingToLan(buffer.readBoolean());
        buffer.readBoolean(); // broadcasting to XBL
        packet.setCommandsEnabled(buffer.readBoolean());
        packet.setTexturePacksRequired(buffer.readBoolean());
        helper.readArray(buffer, packet.getGamerules(), helper::readGameRule);
        packet.setBonusChestEnabled(buffer.readBoolean());
        packet.setStartingWithMap(buffer.readBoolean());
        packet.setTrustingPlayers(buffer.readBoolean());
        packet.setDefaultPlayerPermission(PLAYER_PERMISSIONS[VarInts.readInt(buffer)]);
        packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        packet.setServerChunkTickRange(buffer.readIntLE());
        buffer.readBoolean(); // Broadcasting to Platform
        packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        buffer.readBoolean(); // Intent on XBL broadcast
        packet.setBehaviorPackLocked(buffer.readBoolean());
        packet.setResourcePackLocked(buffer.readBoolean());
        packet.setFromLockedWorldTemplate(buffer.readBoolean());
        packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
    }

    protected long readSeed(ByteBuf buffer) {
        return VarInts.readInt(buffer);
    }

    protected void writeSeed(ByteBuf buffer, long seed) {
        VarInts.writeInt(buffer, (int) seed);
    }
}
