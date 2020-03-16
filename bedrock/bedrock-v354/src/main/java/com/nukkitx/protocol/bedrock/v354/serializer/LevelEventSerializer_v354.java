package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.LevelEventType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelEventSerializer_v354 implements PacketSerializer<LevelEventPacket> {
    public static final LevelEventSerializer_v354 INSTANCE = new LevelEventSerializer_v354();

    private static final InternalLogger log = InternalLoggerFactory.getInstance(LevelEventSerializer_v354.class);
    private static final Int2ObjectBiMap<LevelEventType> events = new Int2ObjectBiMap<>();

    static {
        events.put(1000, SOUND_CLICK);
        events.put(1001, SOUND_CLICK_FAIL);
        events.put(1002, SOUND_SHOOT);
        events.put(1003, SOUND_DOOR);
        events.put(1004, SOUND_FIZZ);
        events.put(1005, SOUND_IGNITE);
        events.put(1007, SOUND_GHAST);
        events.put(1008, SOUND_GHAST_SHOOT);
        events.put(1009, SOUND_BLAZE_SHOOT);
        events.put(1010, SOUND_DOOR_BUMP);
        events.put(1012, SOUND_DOOR_CRASH);
        events.put(1018, SOUND_ENDERMAN_TELEPORT);
        events.put(1020, SOUND_ANVIL_BREAK);
        events.put(1021, SOUND_ANVIL_USE);
        events.put(1022, SOUND_ANVIL_FALL);
        events.put(1030, SOUND_POP);
        events.put(1032, SOUND_PORTAL);
        events.put(1040, SOUND_ITEMFRAME_ADD_ITEM);
        events.put(1041, SOUND_ITEMFRAME_REMOVE);
        events.put(1042, SOUND_ITEMFRAME_PLACE);
        events.put(1043, SOUND_ITEMFRAME_REMOVE_ITEM);
        events.put(1044, SOUND_ITEMFRAME_ROTATE_ITEM);
        events.put(1050, SOUND_CAMERA);
        events.put(1051, SOUND_ORB);
        events.put(1052, SOUND_TOTEM);
        events.put(1060, SOUND_ARMOR_STAND_BREAK);
        events.put(1061, SOUND_ARMOR_STAND_HIT);
        events.put(1062, SOUND_ARMOR_STAND_FALL);
        events.put(1063, SOUND_ARMOR_STAND_PLACE);
        events.put(2000, SHOOT);
        events.put(2001, DESTROY);
        events.put(2002, SPLASH);
        events.put(2003, EYE_DESPAWN);
        events.put(2004, ENTITY_SPAWN);
        events.put(2005, BONEMEAL);
        events.put(2006, GUARDIAN_CURSE);
        events.put(2008, BLOCK_FORCE_FIELD);
        events.put(2009, PROJECTILE_HIT);
        events.put(2010, DRAGON_EGG_TELEPORT);
        events.put(2013, ENDERMAN_TELEPORT);
        events.put(2014, PUNCH_BLOCK);
        events.put(2015, WATER_SPLASH);
        events.put(3001, START_RAIN);
        events.put(3002, START_THUNDER);
        events.put(3003, STOP_RAIN);
        events.put(3004, STOP_THUNDER);
        events.put(3005, PAUSE_GAME);
        events.put(3500, REDSTONE_TRIGGER);
        events.put(3501, CAULDRON_EXPLODE);
        events.put(3502, CAULDRON_DYE_ARMOR);
        events.put(3503, CAULDRON_CLEAN_ARMOR);
        events.put(3504, CAULDRON_FILL_POTION);
        events.put(3505, CAULDRON_TAKE_POTION);
        events.put(3506, CAULDRON_FILL_WATER);
        events.put(3507, CAULDRON_TAKE_WATER);
        events.put(3508, CAULDRON_ADD_DYE);
        events.put(3509, CAULDRON_CLEAN_BANNER);
        events.put(3600, BLOCK_START_BREAK);
        events.put(3601, BLOCK_STOP_BREAK);
        events.put(4000, SET_DATA);
        events.put(9800, PLAYERS_SLEEPING);

        // Particles
        events.put((1 | 0x4000), PARTICLE_BUBBLE);
        events.put((2 | 0x4000), PARTICLE_CRITICAL);
        events.put((3 | 0x4000), PARTICLE_BLOCK_FORCE_FIELD);
        events.put((4 | 0x4000), PARTICLE_SMOKE);
        events.put((5 | 0x4000), PARTICLE_EXPLODE);
        events.put((6 | 0x4000), PARTICLE_EVAPORATION);
        events.put((7 | 0x4000), PARTICLE_FLAME);
        events.put((8 | 0x4000), PARTICLE_LAVA);
        events.put((9 | 0x4000), PARTICLE_LARGE_SMOKE);
        events.put((10 | 0x4000), PARTICLE_REDSTONE);
        events.put((11 | 0x4000), PARTICLE_RISING_RED_DUST);
        events.put((12 | 0x4000), PARTICLE_ITEM_BREAK);
        events.put((13 | 0x4000), PARTICLE_SNOWBALL_POOF);
        events.put((14 | 0x4000), PARTICLE_HUGE_EXPLODE);
        events.put((15 | 0x4000), PARTICLE_HUGE_EXPLODE_SEED);
        events.put((16 | 0x4000), PARTICLE_MOB_FLAME);
        events.put((17 | 0x4000), PARTICLE_HEART);
        events.put((18 | 0x4000), PARTICLE_TERRAIN);
        events.put((19 | 0x4000), PARTICLE_TOWN_AURA);
        events.put((20 | 0x4000), PARTICLE_PORTAL);
        events.put((21 | 0x4000), PARTICLE_SPLASH);
        events.put((22 | 0x4000), PARTICLE_WATER_WAKE);
        events.put((23 | 0x4000), PARTICLE_DRIP_WATER);
        events.put((24 | 0x4000), PARTICLE_DRIP_LAVA);
        events.put((25 | 0x4000), PARTICLE_FALLING_DUST);
        events.put((26 | 0x4000), PARTICLE_MOB_SPELL);
        events.put((27 | 0x4000), PARTICLE_MOB_SPELL_AMBIENT);
        events.put((28 | 0x4000), PARTICLE_MOB_SPELL_INSTANTANEOUS);
        events.put((29 | 0x4000), PARTICLE_INK);
        events.put((30 | 0x4000), PARTICLE_SLIME);
        events.put((31 | 0x4000), PARTICLE_RAIN_SPLASH);
        events.put((32 | 0x4000), PARTICLE_VILLAGER_ANGRY);
        events.put((33 | 0x4000), PARTICLE_VILLAGER_HAPPY);
        events.put((34 | 0x4000), PARTICLE_ENCHANTMENT_TABLE);
        events.put((35 | 0x4000), PARTICLE_TRACKING_EMITTER);
        events.put((36 | 0x4000), PARTICLE_NOTE);
        events.put((37 | 0x4000), PARTICLE_WITCH_SPELL);
        events.put((38 | 0x4000), PARTICLE_CARROT);
        //39 unknown
        events.put((40 | 0x4000), PARTICLE_END_ROD);
        events.put((41 | 0x4000), PARTICLE_DRAGONS_BREATH);
    }

    @Override
    public void serialize(ByteBuf buffer, LevelEventPacket packet) {
        VarInts.writeInt(buffer, events.get(packet.getType()));
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelEventPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setType(events.get(eventId));
        if (packet.getType() == null) {
            log.debug("Unknown Level Event {} received", eventId);
        }
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setData(VarInts.readInt(buffer));
    }
}
