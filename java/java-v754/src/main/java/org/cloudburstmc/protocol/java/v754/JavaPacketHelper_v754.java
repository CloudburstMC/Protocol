package org.cloudburstmc.protocol.java.v754;

import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.data.entity.EntityDataType;
import org.cloudburstmc.protocol.java.data.entity.MobEffectType;
import org.cloudburstmc.protocol.java.data.entity.Pose;
import org.cloudburstmc.protocol.java.data.inventory.ContainerType;
import org.cloudburstmc.protocol.java.data.world.ParticleType;

import static org.cloudburstmc.protocol.java.data.entity.EntityType.*;
import static org.cloudburstmc.protocol.java.data.inventory.ContainerType.*;
import static org.cloudburstmc.protocol.java.data.world.BlockEntityAction.*;

public class JavaPacketHelper_v754 extends JavaPacketHelper {
    public static final JavaPacketHelper INSTANCE = new JavaPacketHelper_v754();

    @Override
    protected void registerEntityTypes() {
        this.entityTypes.put(0, AREA_EFFECT_CLOUD);
        this.entityTypes.put(1, ARMOR_STAND);
        this.entityTypes.put(2, ARROW);
        this.entityTypes.put(3, BAT);
        this.entityTypes.put(4, BEE);
        this.entityTypes.put(5, BOAT);
        this.entityTypes.put(6, BLAZE);
        this.entityTypes.put(7, CAT);
        this.entityTypes.put(8, CAVE_SPIDER);
        this.entityTypes.put(9, CHICKEN);
        this.entityTypes.put(10, COD);
        this.entityTypes.put(11, COW);
        this.entityTypes.put(12, CREEPER);
        this.entityTypes.put(13, DONKEY);
        this.entityTypes.put(14, DOLPHIN);
        this.entityTypes.put(15, DRAGON_FIREBALL);
        this.entityTypes.put(16, DROWNED);
        this.entityTypes.put(17, ELDER_GUARDIAN);
        this.entityTypes.put(18, END_CRYSTAL);
        this.entityTypes.put(19, ENDER_DRAGON);
        this.entityTypes.put(20, ENDERMAN);
        this.entityTypes.put(21, ENDERMITE);
        this.entityTypes.put(22, EVOKER_FANGS);
        this.entityTypes.put(23, EVOKER);
        this.entityTypes.put(24, EXPERIENCE_ORB);
        this.entityTypes.put(25, EYE_OF_ENDER);
        this.entityTypes.put(26, FALLING_BLOCK);
        this.entityTypes.put(27, FIREWORK_ROCKET);
        this.entityTypes.put(28, FOX);
        this.entityTypes.put(29, GHAST);
        this.entityTypes.put(30, GIANT);
        this.entityTypes.put(31, GUARDIAN);
        this.entityTypes.put(32, HORSE);
        this.entityTypes.put(33, HUSK);
        this.entityTypes.put(34, ILLUSIONER);
        this.entityTypes.put(35, ITEM);
        this.entityTypes.put(36, ITEM_FRAME);
        this.entityTypes.put(37, FIREBALL);
        this.entityTypes.put(38, LEASH_KNOT);
        this.entityTypes.put(39, LIGHTNING_BOLT);
        this.entityTypes.put(40, LLAMA);
        this.entityTypes.put(41, LLAMA_SPIT);
        this.entityTypes.put(42, MAGMA_CUBE);
        this.entityTypes.put(43, MINECART);
        this.entityTypes.put(44, MINECART_CHEST);
        this.entityTypes.put(45, MINECART_COMMAND_BLOCK);
        this.entityTypes.put(46, MINECART_FURNACE);
        this.entityTypes.put(47, MINECART_HOPPER);
        this.entityTypes.put(48, MINECART_SPAWNER);
        this.entityTypes.put(49, MINECART_TNT);
        this.entityTypes.put(50, MULE);
        this.entityTypes.put(51, MOOSHROOM);
        this.entityTypes.put(52, OCELOT);
        this.entityTypes.put(53, PAINTING);
        this.entityTypes.put(54, PANDA);
        this.entityTypes.put(55, PARROT);
        this.entityTypes.put(56, PIG);
        this.entityTypes.put(57, PIGLIN);
        this.entityTypes.put(58, PIGLIN_BRUTE);
        this.entityTypes.put(59, PUFFERFISH);
        this.entityTypes.put(60, POLAR_BEAR);
        this.entityTypes.put(61, PRIMED_TNT);
        this.entityTypes.put(62, RABBIT);
        this.entityTypes.put(63, SALMON);
        this.entityTypes.put(64, SHEEP);
        this.entityTypes.put(65, SHULKER);
        this.entityTypes.put(66, SHULKER_BULLET);
        this.entityTypes.put(67, SILVERFISH);
        this.entityTypes.put(68, SKELETON);
        this.entityTypes.put(69, SKELETON_HORSE);
        this.entityTypes.put(70, SLIME);
        this.entityTypes.put(71, SMALL_FIREBALL);
        this.entityTypes.put(72, SNOW_GOLEM);
        this.entityTypes.put(73, SNOWBALL);
        this.entityTypes.put(74, SPECTRAL_ARROW);
        this.entityTypes.put(75, SPIDER);
        this.entityTypes.put(76, SQUID);
        this.entityTypes.put(77, STRAY);
        this.entityTypes.put(78, TRADER_LLAMA);
        this.entityTypes.put(79, TROPICAL_FISH);
        this.entityTypes.put(80, TURTLE);
        this.entityTypes.put(81, THROWN_EGG);
        this.entityTypes.put(82, THROWN_ENDERPEARL);
        this.entityTypes.put(83, THROWN_EXP_BOTTLE);
        this.entityTypes.put(84, THROWN_POTION);
        this.entityTypes.put(85, TRIDENT);
        this.entityTypes.put(86, VEX);
        this.entityTypes.put(87, VILLAGER);
        this.entityTypes.put(88, IRON_GOLEM);
        this.entityTypes.put(89, VINDICATOR);
        this.entityTypes.put(90, PILLAGER);
        this.entityTypes.put(91, WANDERING_TRADER);
        this.entityTypes.put(92, WITCH);
        this.entityTypes.put(93, WITHER);
        this.entityTypes.put(94, WITHER_SKELETON);
        this.entityTypes.put(95, WITHER_SKULL);
        this.entityTypes.put(96, WOLF);
        this.entityTypes.put(97, ZOMBIE);
        this.entityTypes.put(98, ZOMBIE_HORSE);
        this.entityTypes.put(99, ZOMBIFIED_PIGLIN);
        this.entityTypes.put(100, ZOMBIE_VILLAGER);
        this.entityTypes.put(101, PHANTOM);
        this.entityTypes.put(102, RAVAGER);
        this.entityTypes.put(103, HOGLIN);
        this.entityTypes.put(104, STRIDER);
        this.entityTypes.put(105, ZOGLIN);
        this.entityTypes.put(106, PLAYER);
        this.entityTypes.put(107, FISHING_BOBBER);
    }

    @Override
    protected void registerBlockEntityActions() {
        this.blockEntityActions.put(1, MOB_SPAWNER_DATA);
        this.blockEntityActions.put(2, COMMAND_BLOCK_TEXT);
        this.blockEntityActions.put(3, BEACON_LEVEL);
        this.blockEntityActions.put(4, HEAD_ROTATION);
        this.blockEntityActions.put(5, DECLARE_CONDUIT);
        this.blockEntityActions.put(6, BANNER_COLOR);
        this.blockEntityActions.put(7, STRUCTURE_DATA);
        this.blockEntityActions.put(8, GATEWAY_DESTINATION);
        this.blockEntityActions.put(9, SIGN_TEXT);
        this.blockEntityActions.put(11, DECLARE_BED);
        this.blockEntityActions.put(12, JIGSAW_DATA);
        this.blockEntityActions.put(13, CAMPFIRE_ITEMS);
        this.blockEntityActions.put(14, BEEHIVE_INFO);
    }

    @Override
    protected void registerContainerTypes() {
        this.containerTypes.put(0, GENERIC_9X1);
        this.containerTypes.put(1, GENERIC_9X2);
        this.containerTypes.put(2, GENERIC_9X3);
        this.containerTypes.put(3, GENERIC_9X4);
        this.containerTypes.put(4, GENERIC_9X5);
        this.containerTypes.put(5, GENERIC_9X6);
        this.containerTypes.put(6, GENERIC_3X3);
        this.containerTypes.put(7, ANVIL);
        this.containerTypes.put(8, BEACON);
        this.containerTypes.put(9, BLAST_FURNACE);
        this.containerTypes.put(10, BREWING_STAND);
        this.containerTypes.put(11, CRAFTING);
        this.containerTypes.put(12, ENCHANTMENT);
        this.containerTypes.put(13, FURNACE);
        this.containerTypes.put(14, GRINDSTONE);
        this.containerTypes.put(15, HOPPER);
        this.containerTypes.put(16, LECTERN);
        this.containerTypes.put(17, LOOM);
        this.containerTypes.put(18, MERCHANT);
        this.containerTypes.put(19, SHULKER_BOX);
        this.containerTypes.put(20, SMITHING);
        this.containerTypes.put(21, SMOKER);
        this.containerTypes.put(22, CARTOGRAPHY_TABLE);
        this.containerTypes.put(23, STONECUTTER);
    }

    @Override
    protected void registerEntityDataTypes() {
        this.entityDataTypes.put(0, EntityDataType.BYTE);
        this.entityDataTypes.put(1, EntityDataType.INT);
        this.entityDataTypes.put(2, EntityDataType.FLOAT);
        this.entityDataTypes.put(3, EntityDataType.STRING);
        this.entityDataTypes.put(4, EntityDataType.COMPONENT);
        this.entityDataTypes.put(5, EntityDataType.OPTIONAL_COMPONENT);
        this.entityDataTypes.put(6, EntityDataType.ITEM_STACK);
        this.entityDataTypes.put(7, EntityDataType.BOOLEAN);
        this.entityDataTypes.put(8, EntityDataType.ROTATION);
        this.entityDataTypes.put(9, EntityDataType.BLOCK_POS);
        this.entityDataTypes.put(10, EntityDataType.OPTIONAL_BLOCK_POS);
        this.entityDataTypes.put(11, EntityDataType.DIRECTION);
        this.entityDataTypes.put(12, EntityDataType.OPTIONAL_UUID);
        this.entityDataTypes.put(13, EntityDataType.OPTIONAL_BLOCK_STATE);
        this.entityDataTypes.put(14, EntityDataType.NBT);
        this.entityDataTypes.put(15, EntityDataType.PARTICLE);
        this.entityDataTypes.put(16, EntityDataType.VILLAGER_DATA);
        this.entityDataTypes.put(17, EntityDataType.OPTIONAL_INT);
        this.entityDataTypes.put(18, EntityDataType.POSE);
    }

    @Override
    public void registerPoses() {
        this.poses.put(0, Pose.STANDING);
        this.poses.put(1, Pose.FALL_FLYING);
        this.poses.put(2, Pose.SLEEPING);
        this.poses.put(3, Pose.SWIMMING);
        this.poses.put(4, Pose.SPIN_ATTACK);
        this.poses.put(5, Pose.CROUCHING);
        this.poses.put(6, Pose.DYING);
    }

    @Override
    public void registerParticles() {
        this.particles.put(0, ParticleType.AMBIENT_ENTITY_EFFECT);
        this.particles.put(1, ParticleType.ANGRY_VILLAGER);
        this.particles.put(2, ParticleType.BARRIER);
        this.particles.put(3, ParticleType.BLOCK);
        this.particles.put(4, ParticleType.BUBBLE);
        this.particles.put(5, ParticleType.CLOUD);
        this.particles.put(6, ParticleType.CRIT);
        this.particles.put(7, ParticleType.DAMAGE_INDICATOR);
        this.particles.put(8, ParticleType.DRAGON_BREATH);
        this.particles.put(9, ParticleType.DRIPPING_LAVA);
        this.particles.put(10, ParticleType.FALLING_LAVA);
        this.particles.put(11, ParticleType.LANDING_LAVA);
        this.particles.put(12, ParticleType.DRIPPING_WATER);
        this.particles.put(13, ParticleType.FALLING_WATER);
        this.particles.put(14, ParticleType.DUST);
        this.particles.put(15, ParticleType.EFFECT);
        this.particles.put(16, ParticleType.ELDER_GUARDIAN);
        this.particles.put(17, ParticleType.ENCHANTED_HIT);
        this.particles.put(18, ParticleType.ENCHANT);
        this.particles.put(19, ParticleType.END_ROD);
        this.particles.put(20, ParticleType.ENTITY_EFFECT);
        this.particles.put(21, ParticleType.EXPLOSION_EMITTER);
        this.particles.put(22, ParticleType.EXPLOSION);
        this.particles.put(23, ParticleType.FALLING_DUST);
        this.particles.put(24, ParticleType.FIREWORK);
        this.particles.put(25, ParticleType.FISHING);
        this.particles.put(26, ParticleType.FLAME);
        this.particles.put(27, ParticleType.SOUL_FLAME);
        this.particles.put(28, ParticleType.SOUL);
        this.particles.put(29, ParticleType.FLASH);
        this.particles.put(30, ParticleType.HAPPY_VILLAGER);
        this.particles.put(31, ParticleType.COMPOSTER);
        this.particles.put(32, ParticleType.HEART);
        this.particles.put(33, ParticleType.INSTANT_EFFECT);
        this.particles.put(34, ParticleType.ITEM);
        this.particles.put(35, ParticleType.ITEM_SLIME);
        this.particles.put(36, ParticleType.ITEM_SNOWBALL);
        this.particles.put(37, ParticleType.LARGE_SMOKE);
        this.particles.put(38, ParticleType.LAVA);
        this.particles.put(39, ParticleType.MYCELIUM);
        this.particles.put(40, ParticleType.NOTE);
        this.particles.put(41, ParticleType.POOF);
        this.particles.put(42, ParticleType.PORTAL);
        this.particles.put(43, ParticleType.RAIN);
        this.particles.put(44, ParticleType.SMOKE);
        this.particles.put(45, ParticleType.SNEEZE);
        this.particles.put(46, ParticleType.SPIT);
        this.particles.put(47, ParticleType.SQUID_INK);
        this.particles.put(48, ParticleType.SWEEP_ATTACK);
        this.particles.put(49, ParticleType.TOTEM_OF_UNDYING);
        this.particles.put(50, ParticleType.UNDERWATER);
        this.particles.put(51, ParticleType.SPLASH);
        this.particles.put(52, ParticleType.WITCH);
        this.particles.put(53, ParticleType.BUBBLE_POP);
        this.particles.put(54, ParticleType.CURRENT_DOWN);
        this.particles.put(55, ParticleType.BUBBLE_COLUMN_UP);
        this.particles.put(56, ParticleType.NAUTILUS);
        this.particles.put(57, ParticleType.DOLPHIN);
        this.particles.put(58, ParticleType.CAMPFIRE_COSY_SMOKE);
        this.particles.put(59, ParticleType.CAMPFIRE_SIGNAL_SMOKE);
        this.particles.put(60, ParticleType.DRIPPING_HONEY);
        this.particles.put(61, ParticleType.FALLING_HONEY);
        this.particles.put(62, ParticleType.LANDING_HONEY);
        this.particles.put(63, ParticleType.FALLING_NECTAR);
        this.particles.put(64, ParticleType.ASH);
        this.particles.put(65, ParticleType.CRIMSON_SPORE);
        this.particles.put(66, ParticleType.WARPED_SPORE);
        this.particles.put(67, ParticleType.DRIPPING_OBSIDIAN_TEAR);
        this.particles.put(68, ParticleType.FALLING_OBSIDIAN_TEAR);
        this.particles.put(69, ParticleType.LANDING_OBSIDIAN_TEAR);
        this.particles.put(70, ParticleType.REVERSE_PORTAL);
        this.particles.put(71, ParticleType.WHITE_ASH);
    }

    @Override
    public void registerMobEffects() {
        this.mobEffects.put(1, MobEffectType.SPEED);
        this.mobEffects.put(2, MobEffectType.SLOWNESS);
        this.mobEffects.put(3, MobEffectType.HASTE);
        this.mobEffects.put(4, MobEffectType.MINING_FATIGUE);
        this.mobEffects.put(5, MobEffectType.STRENGTH);
        this.mobEffects.put(6, MobEffectType.INSTANT_HEALTH);
        this.mobEffects.put(7, MobEffectType.INSTANT_DAMAGE);
        this.mobEffects.put(8, MobEffectType.JUMP_BOOST);
        this.mobEffects.put(9, MobEffectType.NAUSEA);
        this.mobEffects.put(10, MobEffectType.REGENERATION);
        this.mobEffects.put(11, MobEffectType.DAMAGE_RESISTANCE);
        this.mobEffects.put(12, MobEffectType.FIRE_RESISTANCE);
        this.mobEffects.put(13, MobEffectType.WATER_BREATHING);
        this.mobEffects.put(14, MobEffectType.INVISIBILITY);
        this.mobEffects.put(15, MobEffectType.BLINDNESS);
        this.mobEffects.put(16, MobEffectType.NIGHT_VISION);
        this.mobEffects.put(17, MobEffectType.HUNGER);
        this.mobEffects.put(18, MobEffectType.WEAKNESS);
        this.mobEffects.put(19, MobEffectType.POISON);
        this.mobEffects.put(20, MobEffectType.WITHER);
        this.mobEffects.put(21, MobEffectType.HEALTH_BOOST);
        this.mobEffects.put(22, MobEffectType.ABSORPTION);
        this.mobEffects.put(23, MobEffectType.SATURATION);
        this.mobEffects.put(24, MobEffectType.GLOWING);
        this.mobEffects.put(25, MobEffectType.LEVITATION);
        this.mobEffects.put(26, MobEffectType.LUCK);
        this.mobEffects.put(27, MobEffectType.UNLUCK);
        this.mobEffects.put(28, MobEffectType.SLOW_FALLING);
        this.mobEffects.put(29, MobEffectType.CONDUIT_POWER);
        this.mobEffects.put(30, MobEffectType.DOLPHINS_GRACE);
        this.mobEffects.put(31, MobEffectType.BAD_OMEN);
        this.mobEffects.put(32, MobEffectType.HERO_OF_THE_VILLAGE);
    }
}
