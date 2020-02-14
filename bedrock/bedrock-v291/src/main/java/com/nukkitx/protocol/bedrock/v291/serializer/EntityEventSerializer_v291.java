package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.EntityEventType;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.EntityEventType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityEventSerializer_v291 implements PacketSerializer<EntityEventPacket> {
    public static final EntityEventSerializer_v291 INSTANCE = new EntityEventSerializer_v291();
    private static final TIntHashBiMap<EntityEventType> events = new TIntHashBiMap<>();
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityEventSerializer_v291.class);

    static {
        events.put(2, HURT_ANIMATION);
        events.put(3, DEATH_ANIMATION);
        events.put(4, ARM_SWING);
        events.put(6, TAME_FAIL);
        events.put(7, TAME_SUCCESS);
        events.put(8, SHAKE_WET);
        events.put(9, USE_ITEM);
        events.put(10, EAT_BLOCK_ANIMATION);
        events.put(11, FISH_HOOK_BUBBLE);
        events.put(12, FISH_HOOK_POSITION);
        events.put(13, FISH_HOOK_HOOK);
        events.put(14, FISH_HOOK_LURED);
        events.put(15, SQUID_INK_CLOUD);
        events.put(16, ZOMBIE_VILLAGER_CURE);
        events.put(18, RESPAWN);
        events.put(19, IRON_GOLEM_OFFER_FLOWER);
        events.put(20, IRON_GOLEM_WITHDRAW_FLOWER);
        events.put(21, LOVE_PARTICLES);
        events.put(22, VILLAGER_HURT);
        events.put(23, VILLAGER_STOP_TRADING);
        events.put(24, WITCH_SPELL_PARTICLES);
        events.put(25, FIREWORK_PARTICLES);
        events.put(27, SILVERFISH_MERGE_WITH_STONE);
        events.put(28, GUARDIAN_ATTACK_ANIMATION);
        events.put(29, WITCH_DRINK_POTION);
        events.put(30, WITCH_THROW_POTION);
        events.put(31, MINECART_TNT_PRIME_FUSE);
        events.put(34, PLAYER_ADD_XP_LEVELS);
        events.put(35, ELDER_GUARDIAN_CURSE);
        events.put(36, AGENT_ARM_SWING);
        events.put(37, ENDER_DRAGON_DEATH);
        events.put(38, DUST_PARTICLES);
        events.put(39, ARROW_SHAKE);
        events.put(57, EATING_ITEM);
        events.put(60, BABY_ANIMAL_FEED);
        events.put(61, DEATH_SMOKE_CLOUD);
        events.put(62, COMPLETE_TRADE);
        events.put(63, REMOVE_LEASH);
        events.put(64, CARAVAN);
        events.put(65, CONSUME_TOTEM);
        events.put(66, CHECK_TREASURE_HUNTER_ACHIEVEMENT);
        events.put(67, ENTITY_SPAWN);
        events.put(68, DRAGON_FLAMING);
        events.put(69, MERGE_ITEMS);
        events.put(71, BALLOON_POP);
        events.put(72, FIND_TREASURE_BRIBE);
    }

    @Override
    public void serialize(ByteBuf buffer, EntityEventPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(events.get(packet.getType()));
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, EntityEventPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int event = buffer.readUnsignedByte();
        packet.setType(events.get(event));
        packet.setData(VarInts.readInt(buffer));
        if (packet.getType() == null) {
            log.debug("Unknown EntityEvent {} in packet {}", event, packet);
        }
    }
}
