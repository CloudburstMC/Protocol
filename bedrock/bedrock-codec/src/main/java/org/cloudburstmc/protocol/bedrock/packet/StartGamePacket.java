package org.cloudburstmc.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.defintions.ItemDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true, exclude = {"itemDefinitions", "blockPalette"})
public class StartGamePacket implements BedrockPacket {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(StartGamePacket.class);

    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
    private long uniqueEntityId;
    private long runtimeEntityId;
    private GameType playerGameType;
    private Vector3f playerPosition;
    private Vector2f rotation;
    // Level settings start
    private int seed;
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
    private boolean forceExperimentalGameplay;
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

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME;
    }
}
