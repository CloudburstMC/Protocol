package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtList;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true, exclude = {"itemDefinitions", "blockPalette"})
public class StartGamePacket implements BedrockPacket {
    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private GameType playerGameType;
    private Vector3f playerPosition;
    private Vector2f rotation;
    // Level settings start
    private long seed;
    private SpawnBiomeType spawnBiomeType;
    private String customBiomeName;
    private int dimensionId;
    private int generatorId;
    private GameType levelGameType;
    private int difficulty;
    private Vector3i defaultSpawn;
    private boolean achievementsDisabled;
    private int dayCycleStopTime;
    private int eduEditionOffers;
    private boolean eduFeaturesEnabled;
    private String educationProductionId;
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
    /**
     * @since v465
     */
    private EduSharedUriResource eduSharedUriResource = EduSharedUriResource.EMPTY;
    private OptionalBoolean forceExperimentalGameplay;
    /**
     * @since 1.19.20
     */
    private ChatRestrictionLevel chatRestrictionLevel;
    /**
     * @since 1.19.20
     */
    private boolean disablingPlayerInteractions;
    /**
     * @since 1.19.20
     */
    private boolean disablingPersonas;
    /**
     * @since 1.19.20
     */
    private boolean disablingCustomSkins;
    // Level settings end
    private String levelId;
    private String levelName;
    private String premiumWorldTemplateId;
    private boolean trial;
    // SyncedPlayerMovementSettings start
    private AuthoritativeMovementMode authoritativeMovementMode;
    private int rewindHistorySize;
    boolean serverAuthoritativeBlockBreaking;
    // SyncedPlayerMovementSettings end
    private long currentTick;
    private int enchantmentSeed;
    private NbtList<NbtMap> blockPalette;
    private final List<BlockPropertyData> blockProperties = new ObjectArrayList<>();
    private List<ItemDefinition> itemDefinitions = new ObjectArrayList<>();
    private String multiplayerCorrelationId;
    /**
     * @since v407
     */
    private boolean inventoriesServerAuthoritative;
    /**
     * The name of the server software.
     * Used for telemetry within the Bedrock client.
     *
     * @since v440
     */
    private String serverEngine;
    /**
     * @since v527
     */
    private NbtMap playerPropertyData;
    /**
     * A XXHash64 of all block states by their compound tag.
     * <b>The exact way this is calculated is not currently known.</b>
     * <p>
     * A value of 0 will not be validated by the client.
     *
     * @since v475
     */
    private long blockRegistryChecksum;
    /**
     * @since v527
     */
    private UUID worldTemplateId;
    /**
     * @since v534
     */
    private boolean worldEditor;
    /**
     * Enables client side chunk generation
     *
     * @since 1.19.20
     */
    private boolean clientSideGenerationEnabled;
    /**
     * @since v567
     */
    private boolean emoteChatMuted;
    /**
     * Whether block runtime IDs should be replaced by 32-bit integer hashes of NBT block state.
     * Unlike runtime IDs, this hashes should be persistent across versions and should make support for data-driven/custom blocks easier.
     *
     * @since v582
     */
    private boolean blockNetworkIdsHashed;
    /**
     * @since v582
     */
    private boolean createdInEditor;
    /**
     * @since v582
     */
    private boolean exportedFromEditor;
    /**
     * @since v589
     */
    private NetworkPermissions networkPermissions = NetworkPermissions.DEFAULT;
    /**
     * @since v671
     */
    private boolean hardcore;
    /**
     * @since v685
     */
    private String serverId;
    /**
     * @since v685
     */
    private String worldId;
    /**
     * @since v685
     */
    private String scenarioId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME;
    }

    @Override
    public StartGamePacket clone() {
        try {
            return (StartGamePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

