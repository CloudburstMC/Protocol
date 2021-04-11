package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(exclude = {"itemEntries", "blockPalette"})
public class StartGamePacket extends BedrockPacket {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(StartGamePacket.class);

    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private GameType playerGameType;
    private Vector3f playerPosition;
    private Vector2f rotation;
    // Level settings start
    private int seed;
    private SpawnBiomeType spawnBiomeType = SpawnBiomeType.DEFAULT;
    private String customBiomeName = "";
    private int dimensionId;
    private int generatorId;
    private GameType levelGameType;
    private int difficulty;
    private Vector3i defaultSpawn;
    private boolean achievementsDisabled;
    private int dayCycleStopTime;
    private int eduEditionOffers;
    private boolean eduFeaturesEnabled;
    private String educationProductionId = "";
    private float rainLevel;
    private float lightningLevel;
    private boolean platformLockedContentConfirmed;
    private boolean multiplayerGame;
    private boolean broadcastingToLan;
    private GamePublishSetting xblBroadcastMode;
    private GamePublishSetting platformBroadcastMode;
    private boolean commandsEnabled;
    private boolean texturePacksRequired;
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    private boolean experimentsPreviouslyToggled;
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
    private int limitedWorldWidth;
    private int limitedWorldHeight;
    private boolean netherType;
    private boolean forceExperimentalGameplay;
    // Level settings end
    private String levelId;
    private String levelName;
    private String premiumWorldTemplateId;
    private boolean trial;
    /**
     * @deprecated as of v428
     */
    private AuthoritativeMovementMode authoritativeMovementMode;
    /**
     * @since v428
     */
    private SyncedPlayerMovementSettings playerMovementSettings;
    private long currentTick;
    private int enchantmentSeed;
    private NbtList<NbtMap> blockPalette;
    private final List<BlockPropertyData> blockProperties = new ObjectArrayList<>();
    private List<ItemEntry> itemEntries = new ObjectArrayList<>();
    private String multiplayerCorrelationId;
    /**
     * @since v407
     */
    private boolean inventoriesServerAuthoritative;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME;
    }

    @Value
    @AllArgsConstructor
    public static class ItemEntry {
        private final String identifier;
        private final short id;
        private final boolean componentBased;

        public ItemEntry(String identifier, short id) {
            this.identifier = identifier;
            this.id = id;
            this.componentBased = false;
        }
    }
}
