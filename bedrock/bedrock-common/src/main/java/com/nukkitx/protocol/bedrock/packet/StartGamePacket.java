package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.ListTag;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(exclude = {"itemEntries", "blockPalette"})
public class StartGamePacket extends BedrockPacket {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(StartGamePacket.class);

    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
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
    private String unknownString0;
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
    private int unknownInt0;
    private int unknownInt1;
    // Level settings end
    private String levelId;
    private String worldName;
    private String premiumWorldTemplateId;
    private boolean trial;
    private boolean movementServerAuthoritative;
    private long currentTick;
    private int enchantmentSeed;
    private ListTag<CompoundTag> blockPalette;
    private List<ItemEntry> itemEntries = new ObjectArrayList<>();
    private String multiplayerCorrelationId;
    private boolean unknownBool0;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME_PACKET;
    }

    @Value
    public static class ItemEntry {
        private final String identifier;
        private final short id;
    }
}
