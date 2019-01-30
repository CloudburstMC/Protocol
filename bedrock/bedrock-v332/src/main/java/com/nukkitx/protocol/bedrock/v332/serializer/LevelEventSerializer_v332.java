package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.LevelEventPacket.Event;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelEventSerializer_v332 implements PacketSerializer<LevelEventPacket> {
    public static final LevelEventSerializer_v332 INSTANCE = new LevelEventSerializer_v332();

    private static final InternalLogger log = InternalLoggerFactory.getInstance(LevelEventSerializer_v332.class);
    private static final TIntHashBiMap<Event> events = new TIntHashBiMap<>();

    static {
        events.put(1000, Event.SOUND_CLICK);
        events.put(1001, Event.SOUND_CLICK_FAIL);
        events.put(1002, Event.SOUND_SHOOT);
        events.put(1003, Event.SOUND_DOOR);
        events.put(1004, Event.SOUND_FIZZ);
        events.put(1005, Event.SOUND_IGNITE);
        events.put(1007, Event.SOUND_GHAST);
        events.put(1008, Event.SOUND_GHAST_SHOOT);
        events.put(1009, Event.SOUND_BLAZE_SHOOT);
        events.put(1010, Event.SOUND_DOOR_BUMP);
        events.put(1012, Event.SOUND_DOOR_CRASH);
        events.put(1018, Event.SOUND_ENDERMAN_TELEPORT);
        events.put(1020, Event.SOUND_ANVIL_BREAK);
        events.put(1021, Event.SOUND_ANVIL_USE);
        events.put(1022, Event.SOUND_ANVIL_FALL);
        events.put(1030, Event.SOUND_POP);
        events.put(1032, Event.SOUND_PORTAL);
        events.put(1040, Event.SOUND_ITEMFRAME_ADD_ITEM);
        events.put(1041, Event.SOUND_ITEMFRAME_REMOVE);
        events.put(1042, Event.SOUND_ITEMFRAME_PLACE);
        events.put(1043, Event.SOUND_ITEMFRAME_REMOVE_ITEM);
        events.put(1044, Event.SOUND_ITEMFRAME_ROTATE_ITEM);
        events.put(1050, Event.SOUND_CAMERA);
        events.put(1051, Event.SOUND_ORB);
        events.put(1052, Event.SOUND_TOTEM);
        events.put(1060, Event.SOUND_ARMOR_STAND_BREAK);
        events.put(1061, Event.SOUND_ARMOR_STAND_HIT);
        events.put(1062, Event.SOUND_ARMOR_STAND_FALL);
        events.put(1063, Event.SOUND_ARMOR_STAND_PLACE);
        events.put(2000, Event.PARTICLE_SHOOT);
        events.put(2001, Event.PARTICLE_DESTROY);
        events.put(2002, Event.PARTICLE_SPLASH);
        events.put(2003, Event.PARTICLE_EYE_DESPAWN);
        events.put(2004, Event.PARTICLE_SPAWN);
        events.put(2006, Event.GUARDIAN_CURSE);
        events.put(2008, Event.PARTICLE_BLOCK_FORCE_FIELD);
        events.put(2014, Event.PARTICLE_PUNCH_BLOCK);
        events.put(2015, Event.PARTICLE_WATER_SPLASH);
        events.put(3001, Event.START_RAIN);
        events.put(3002, Event.START_THUNDER);
        events.put(3003, Event.STOP_RAIN);
        events.put(3004, Event.STOP_THUNDER);
        events.put(3005, Event.PAUSE_GAME);
        events.put(3500, Event.REDSTONE_TRIGGER);
        events.put(3501, Event.CAULDRON_EXPLODE);
        events.put(3502, Event.CAULDRON_DYE_ARMOR);
        events.put(3503, Event.CAULDRON_CLEAN_ARMOR);
        events.put(3504, Event.CAULDRON_FILL_POTION);
        events.put(3505, Event.CAULDRON_TAKE_POTION);
        events.put(3506, Event.CAULDRON_FILL_WATER);
        events.put(3507, Event.CAULDRON_TAKE_WATER);
        events.put(3508, Event.CAULDRON_ADD_DYE);
        events.put(3509, Event.CAULDRON_CLEAN_BANNER);
        events.put(3600, Event.BLOCK_START_BREAK);
        events.put(3601, Event.BLOCK_STOP_BREAK);
        events.put(4000, Event.SET_DATA);
        events.put(9800, Event.PLAYERS_SLEEPING);
    }

    @Override
    public void serialize(ByteBuf buffer, LevelEventPacket packet) {
        VarInts.writeInt(buffer, events.get(packet.getEvent()));
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelEventPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setEvent(events.get(eventId));
        if (packet.getEvent() == null) {
            log.debug("Unknown Level Event {} received", eventId);
        }
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setData(VarInts.readInt(buffer));
    }
}
