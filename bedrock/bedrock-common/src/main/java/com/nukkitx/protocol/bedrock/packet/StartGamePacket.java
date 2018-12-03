package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GameRule;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"paletteEntries"})
public class StartGamePacket extends BedrockPacket {
    protected final List<GameRule> gamerules = new ArrayList<>();
    protected long uniqueEntityId;
    protected long runtimeEntityId;
    protected int playerGamemode;
    protected Vector3f playerPosition;
    protected Vector2f rotation;
    // Level settings start
    protected int seed;
    protected int dimensionId;
    protected int generatorId;
    protected int levelGamemode;
    protected int difficulty;
    protected Vector3i defaultSpawn;
    protected boolean acheivementsDisabled;
    protected int time;
    protected boolean eduLevel;
    protected boolean eduFeaturesEnabled;
    protected float rainLevel;
    protected float lightningLevel;
    protected boolean multiplayerGame;
    protected boolean broadcastingToLan;
    protected boolean broadcastingToXbl;
    protected boolean commandsEnabled;
    protected boolean texturePacksRequired;
    protected boolean bonusChestEnabled;
    protected boolean startingWithMap;
    protected boolean trustingPlayers;
    protected int defaultPlayerPermission;
    protected int xblBroadcastMode;
    protected int serverChunkTickRange;
    protected boolean broadcastingToPlatform;
    protected int platformBroadcastMode;
    protected boolean intentOnXblBroadcast;
    protected boolean behaviorPackLocked;
    protected boolean resourcePackLocked;
    protected boolean fromLockedWorldTemplate;
    protected boolean usingMsaGamertagsOnly;
    // Level settings end
    protected String levelId;
    protected String worldName;
    protected String premiumWorldTemplateId;
    protected boolean trial;
    protected long currentTick;
    protected int enchantmentSeed;
    protected ByteBuf cachedPalette;
    protected Collection<PaletteEntry> paletteEntries = new ArrayDeque<>();
    protected String multiplayerCorrelationId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Value
    public static class PaletteEntry {
        private final String identifier;
        private final short meta;
    }
}
