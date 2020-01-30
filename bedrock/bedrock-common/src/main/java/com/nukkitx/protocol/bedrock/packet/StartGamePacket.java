package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.ListTag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.data.GameRule;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"itemEntries", "blockPalette"})
public class StartGamePacket extends BedrockPacket {
    private final List<GameRule> gamerules = new ArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private int playerGamemode;
    private Vector3f playerPosition;
    private Vector2f rotation;
    // Level settings start
    private int seed;
    private int dimensionId;
    private int generatorId;
    private int levelGamemode;
    private int difficulty;
    private Vector3i defaultSpawn;
    private boolean achievementsDisabled;
    private int time;
    private int eduEditionOffers;
    private boolean eduFeaturesEnabled;
    private float rainLevel;
    private float lightningLevel;
    private boolean platformLockedContentConfirmed;
    private boolean multiplayerGame;
    private boolean broadcastingToLan;
    private GamePublishSetting xblBroadcastMode;
    private GamePublishSetting platformBroadcastMode;
    private boolean commandsEnabled;
    private boolean texturePacksRequired;
    private boolean bonusChestEnabled;
    private boolean startingWithMap;
    private boolean trustingPlayers;
    private PlayerPermission defaultPlayerPermission;
    private int serverChunkTickRange;
    private boolean behaviorPackLocked;
    private boolean resourcePackLocked;
    private boolean fromLockedWorldTemplate;
    private boolean usingMsaGamertagsOnly;
    private boolean fromWorldTemplate;
    private boolean worldTemplateOptionLocked;
    private boolean onlySpawningV1Villagers;
    private String vanillaVersion;
    // Level settings end
    private String levelId;
    private String worldName;
    private String premiumWorldTemplateId;
    private boolean trial;
    private boolean movementServerAuthoritative;
    private long currentTick;
    private int enchantmentSeed;
    private ListTag<CompoundTag> blockPalette;
    private Collection<ItemEntry> itemEntries = new ArrayDeque<>();
    private String multiplayerCorrelationId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME_PACKET;
    }

    @Value
    @RequiredArgsConstructor
    public static class BlockPaletteEntry {
        private final String identifier;
        private final short meta;
        private final short legacyId;

        public BlockPaletteEntry(String identifier, short meta) {
            this.identifier = identifier;
            this.meta = meta;
            this.legacyId = -1;
        }
    }

    @Value
    public static class ItemEntry {
        private final String identifier;
        private final short id;
    }
}
