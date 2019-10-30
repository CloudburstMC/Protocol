package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.EntityEventPacket.Event;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityEventSerializer_v388 implements PacketSerializer<EntityEventPacket> {
    public static final EntityEventSerializer_v388 INSTANCE = new EntityEventSerializer_v388();
    private static final TIntHashBiMap<Event> events = new TIntHashBiMap<>();
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityEventSerializer_v388.class);

    static {
        events.put(2, Event.HURT_ANIMATION);
        events.put(3, Event.DEATH_ANIMATION);
        events.put(4, Event.ARM_SWING);
        events.put(6, Event.TAME_FAIL);
        events.put(7, Event.TAME_SUCCESS);
        events.put(8, Event.SHAKE_WET);
        events.put(9, Event.USE_ITEM);
        events.put(10, Event.EAT_BLOCK_ANIMATION);
        events.put(11, Event.FISH_HOOK_BUBBLE);
        events.put(12, Event.FISH_HOOK_POSITION);
        events.put(13, Event.FISH_HOOK_HOOK);
        events.put(14, Event.FISH_HOOK_LURED);
        events.put(15, Event.SQUID_INK_CLOUD);
        events.put(16, Event.ZOMBIE_VILLAGER_CURE);
        events.put(18, Event.RESPAWN);
        events.put(19, Event.IRON_GOLEM_OFFER_FLOWER);
        events.put(20, Event.IRON_GOLEM_WITHDRAW_FLOWER);
        events.put(21, Event.LOVE_PARTICLES);
        events.put(22, Event.VILLAGER_HURT);
        events.put(23, Event.VILLAGER_STOP_TRADING);
        events.put(24, Event.WITCH_SPELL_PARTICLES);
        events.put(25, Event.FIREWORK_PARTICLES);
        events.put(27, Event.SILVERFISH_MERGE_WITH_STONE);
        events.put(28, Event.GUARDIAN_ATTACK_ANIMATION);
        events.put(29, Event.WITCH_DRINK_POTION);
        events.put(30, Event.WITCH_THROW_POTION);
        events.put(31, Event.MINECART_TNT_PRIME_FUSE);
        events.put(34, Event.PLAYER_ADD_XP_LEVELS);
        events.put(35, Event.ELDER_GUARDIAN_CURSE);
        events.put(36, Event.AGENT_ARM_SWING);
        events.put(37, Event.ENDER_DRAGON_DEATH);
        events.put(38, Event.DUST_PARTICLES);
        events.put(39, Event.ARROW_SHAKE);
        events.put(57, Event.EATING_ITEM);
        events.put(60, Event.BABY_ANIMAL_FEED);
        events.put(61, Event.DEATH_SMOKE_CLOUD);
        events.put(62, Event.COMPLETE_TRADE);
        events.put(63, Event.REMOVE_LEASH);
        events.put(64, Event.CARAVAN);
        events.put(65, Event.CONSUME_TOTEM);
        events.put(66, Event.CHECK_TREASURE_HUNTER_ACHIEVEMENT);
        events.put(67, Event.ENTITY_SPAWN);
        events.put(68, Event.DRAGON_FLAMING);
        events.put(69, Event.MERGE_ITEMS);
        events.put(71, Event.BALLOON_POP);
        events.put(72, Event.FIND_TREASURE_BRIBE);
    }

    @Override
    public void serialize(ByteBuf buffer, EntityEventPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(events.get(packet.getEvent()));
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, EntityEventPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int event = buffer.readUnsignedByte();
        packet.setEvent(events.get(event));
        packet.setData(VarInts.readInt(buffer));
        if (packet.getEvent() == null) {
            log.debug("Unknown EntityEvent {} in packet {}", event, packet);
        }
    }
}
