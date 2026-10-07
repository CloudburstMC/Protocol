package org.cloudburstmc.protocol.bedrock.data;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("DeprecatedIsStillUsed")
public enum SoundEvent {
    ITEM_USE_ON("item.use.on"),
    HIT("hit"),
    STEP("step"),
    FLY("fly"),
    JUMP("jump"),
    BREAK("break"),
    PLACE("place"),
    HEAVY_STEP("heavy.step"),
    GALLOP("gallop"),
    FALL("fall"),
    AMBIENT("ambient"),
    AMBIENT_BABY("ambient.baby"),
    AMBIENT_IN_WATER("ambient.in.water"),
    BREATHE("breathe"),
    DEATH("death"),
    DEATH_IN_WATER("death.in.water"),
    DEATH_TO_ZOMBIE("death.to.zombie"),
    HURT("hurt"),
    HURT_IN_WATER("hurt.in.water"),
    MAD("mad"),
    BOOST("boost"),
    BOW("bow"),
    SQUISH_BIG("squish.big"),
    SQUISH_SMALL("squish.small"),
    FALL_BIG("fall.big"),
    FALL_SMALL("fall.small"),
    SPLASH("splash"),
    FIZZ("fizz"),
    FLAP("flap"),
    SWIM("swim"),
    DRINK("drink"),
    EAT("eat"),
    TAKEOFF("takeoff"),
    SHAKE("shake"),
    PLOP("plop"),
    LAND("land"),
    SADDLE("saddle"),
    ARMOR("armor"),
    MOB_ARMOR_STAND_PLACE("mob.armor_stand.place"),
    ADD_CHEST("add.chest"),
    THROW("throw"),
    ATTACK("attack"),
    ATTACK_NODAMAGE("attack.nodamage"),
    ATTACK_STRONG("attack.strong"),
    WARN("warn"),
    SHEAR("shear"),
    MILK("milk"),
    THUNDER("thunder"),
    EXPLODE("explode"),
    FIRE("fire"),
    IGNITE("ignite"),
    FUSE("fuse"),
    STARE("stare"),
    SPAWN("spawn"),
    SHOOT("shoot"),
    BREAK_BLOCK("break.block"),
    LAUNCH("launch"),
    BLAST("blast"),
    LARGE_BLAST("large.blast"),
    TWINKLE("twinkle"),
    REMEDY("remedy"),
    UNFECT("unfect"),
    LEVELUP("levelup"),
    BOW_HIT("bow.hit"),
    BULLET_HIT("bullet.hit"),
    EXTINGUISH_FIRE("extinguish.fire"),
    ITEM_FIZZ("item.fizz"),
    CHEST_OPEN("chest.open"),
    CHEST_CLOSED("chest.closed"),
    SHULKERBOX_OPEN("shulkerbox.open"),
    SHULKERBOX_CLOSED("shulkerbox.closed"),
    ENDERCHEST_OPEN("enderchest.open"),
    ENDERCHEST_CLOSED("enderchest.closed"),
    POWER_ON("power.on"),
    POWER_OFF("power.off"),
    ATTACH("attach"),
    DETACH("detach"),
    DENY("deny"),
    TRIPOD("tripod"),
    POP("pop"),
    DROP_SLOT("drop.slot"),
    NOTE("note"),
    THORNS("thorns"),
    PISTON_IN("piston.in"),
    PISTON_OUT("piston.out"),
    PORTAL("portal"),
    WATER("water"),
    LAVA_POP("lava.pop"),
    LAVA("lava"),
    BURP("burp"),
    BUCKET_FILL_WATER("bucket.fill.water"),
    BUCKET_FILL_LAVA("bucket.fill.lava"),
    BUCKET_EMPTY_WATER("bucket.empty.water"),
    BUCKET_EMPTY_LAVA("bucket.empty.lava"),
    ARMOR_EQUIP_CHAIN("armor.equip_chain"),
    ARMOR_EQUIP_DIAMOND("armor.equip_diamond"),
    ARMOR_EQUIP_GENERIC("armor.equip_generic"),
    ARMOR_EQUIP_GOLD("armor.equip_gold"),
    ARMOR_EQUIP_IRON("armor.equip_iron"),
    ARMOR_EQUIP_LEATHER("armor.equip_leather"),
    ARMOR_EQUIP_ELYTRA("armor.equip_elytra"),
    RECORD_13("record.13"),
    RECORD_CAT("record.cat"),
    RECORD_BLOCKS("record.blocks"),
    RECORD_CHIRP("record.chirp"),
    RECORD_FAR("record.far"),
    RECORD_MALL("record.mall"),
    RECORD_MELLOHI("record.mellohi"),
    RECORD_STAL("record.stal"),
    RECORD_STRAD("record.strad"),
    RECORD_WARD("record.ward"),
    RECORD_11("record.11"),
    RECORD_WAIT("record.wait"),
    STOP_RECORD("record.null"),
    FLOP("flop"),
    ELDERGUARDIAN_CURSE("elderguardian.curse"),
    MOB_WARNING("mob.warning"),
    MOB_WARNING_BABY("mob.warning.baby"),
    TELEPORT("teleport"),
    SHULKER_OPEN("shulker.open"),
    SHULKER_CLOSE("shulker.close"),
    HAGGLE("haggle"),
    HAGGLE_YES("haggle.yes"),
    HAGGLE_NO("haggle.no"),
    HAGGLE_IDLE("haggle.idle"),
    CHORUS_GROW("chorusgrow"),
    CHORUS_DEATH("chorusdeath"),
    GLASS("glass"),
    POTION_BREWED("potion.brewed"),
    CAST_SPELL("cast.spell"),
    PREPARE_ATTACK("prepare.attack"),
    PREPARE_SUMMON("prepare.summon"),
    PREPARE_WOLOLO("prepare.wololo"),
    FANG("fang"),
    CHARGE("charge"),
    CAMERA_TAKE_PICTURE("camera.take_picture"),
    LEASHKNOT_PLACE("leashknot.place"),
    LEASHKNOT_BREAK("leashknot.break"),
    GROWL("growl"),
    WHINE("whine"),
    PANT("pant"),
    PURR("purr"),
    PURREOW("purreow"),
    DEATH_MIN_VOLUME("death.min.volume"),
    DEATH_MID_VOLUME("death.mid.volume"),
    IMITATE_BLAZE("imitate.blaze"),
    IMITATE_CAVE_SPIDER("imitate.cave_spider"),
    IMITATE_CREEPER("imitate.creeper"),
    IMITATE_ELDER_GUARDIAN("imitate.elder_guardian"),
    IMITATE_ENDER_DRAGON("imitate.ender_dragon"),
    IMITATE_ENDERMAN("imitate.enderman"),
    IMITATE_ENDERMITE("imitate.endermite"),
    IMITATE_EVOCATION_ILLAGER("imitate.evocation_illager"),
    IMITATE_GHAST("imitate.ghast"),
    IMITATE_HUSK("imitate.husk"),
    /**
     * @deprecated since v1001
     */
    IMITATE_ILLUSION_ILLAGER("undefined"),
    IMITATE_MAGMA_CUBE("imitate.magma_cube"),
    IMITATE_POLAR_BEAR("imitate.polar_bear"),
    IMITATE_SHULKER("imitate.shulker"),
    IMITATE_SILVERFISH("imitate.silverfish"),
    IMITATE_SKELETON("imitate.skeleton"),
    IMITATE_SLIME("imitate.slime"),
    IMITATE_SPIDER("imitate.spider"),
    IMITATE_STRAY("imitate.stray"),
    IMITATE_VEX("imitate.vex"),
    IMITATE_VINDICATION_ILLAGER("imitate.vindication_illager"),
    IMITATE_WITCH("imitate.witch"),
    IMITATE_WITHER("imitate.wither"),
    IMITATE_WITHER_SKELETON("imitate.wither_skeleton"),
    IMITATE_WOLF("imitate.wolf"),
    IMITATE_ZOMBIE("imitate.zombie"),
    IMITATE_ZOMBIE_PIGMAN("imitate.zombie_pigman"),
    IMITATE_ZOMBIE_VILLAGER("imitate.zombie_villager"),
    BLOCK_END_PORTAL_FRAME_FILL("block.end_portal_frame.fill"),
    BLOCK_END_PORTAL_SPAWN("block.end_portal.spawn"),
    RANDOM_ANVIL_USE("random.anvil_use"),
    BOTTLE_DRAGONBREATH("bottle.dragonbreath"),
    PORTAL_TRAVEL("portal.travel"),
    ITEM_TRIDENT_HIT("item.trident.hit"),
    ITEM_TRIDENT_RETURN("item.trident.return"),
    ITEM_TRIDENT_RIPTIDE_1("item.trident.riptide_1"),
    ITEM_TRIDENT_RIPTIDE_2("item.trident.riptide_2"),
    ITEM_TRIDENT_RIPTIDE_3("item.trident.riptide_3"),
    ITEM_TRIDENT_THROW("item.trident.throw"),
    ITEM_TRIDENT_THUNDER("item.trident.thunder"),
    ITEM_TRIDENT_HIT_GROUND("item.trident.hit_ground"),
    DEFAULT("default"),
    ELEMENT_CONSTRUCTOR_OPEN("elemconstruct.open"),
    FLETCHING_TABLE_USE("block.fletching_table.use"),
    ICE_BOMB_HIT("icebomb.hit"),
    BALLOON_POP("balloonpop"),
    LT_REACTION_ICE_BOMB("lt.reaction.icebomb"),
    LT_REACTION_BLEACH("lt.reaction.bleach"),
    LT_REACTION_E_PASTE("lt.reaction.epaste"),
    LT_REACTION_E_PASTE2("lt.reaction.epaste2"),
    LT_REACTION_FERTILIZER("lt.reaction.fertilizer"),
    LT_REACTION_FIREBALL("lt.reaction.fireball"),
    LT_REACTION_MG_SALT("lt.reaction.mgsalt"),
    LT_REACTION_MISC_FIRE("lt.reaction.miscfire"),
    LT_REACTION_FIRE("lt.reaction.fire"),
    LT_REACTION_MISC_EXPLOSION("lt.reaction.miscexplosion"),
    LT_REACTION_MISC_MYSTICAL("lt.reaction.miscmystical"),
    LT_REACTION_MISC_MYSTICAL2("lt.reaction.miscmystical2"),
    LT_REACTION_PRODUCT("lt.reaction.product"),
    SPARKLER_USE("sparkler.use"),
    GLOWSTICK_USE("glowstick.use"),
    SPARKLER_ACTIVE("sparkler.active"),
    CONVERT_TO_DROWNED("convert_to_drowned"),
    BUCKET_FILL_FISH("bucket.fill.fish"),
    BUCKET_EMPTY_FISH("bucket.empty.fish"),
    BUBBLE_UP("bubble.up"),
    BUBBLE_DOWN("bubble.down"),
    BUBBLE_POP("bubble.pop"),
    BUBBLE_UP_INSIDE("bubble.upinside"),
    BUBBLE_DOWN_INSIDE("bubble.downinside"),
    BABY_HURT("hurt.baby"),
    BABY_DEATH("death.baby"),
    BABY_STEP("step.baby"),
    /**
     * @deprecated since v1001
     */
    BABY_SPAWN("undefined"),
    BORN("born"),
    BLOCK_TURTLE_EGG_BREAK("block.turtle_egg.break"),
    BLOCK_TURTLE_EGG_CRACK("block.turtle_egg.crack"),
    BLOCK_TURTLE_EGG_HATCH("block.turtle_egg.hatch"),
    TURTLE_LAY_EGG("lay_egg"),
    BLOCK_TURTLE_EGG_ATTACK("block.turtle_egg.attack"),
    BEACON_ACTIVATE("beacon.activate"),
    BEACON_AMBIENT("beacon.ambient"),
    BEACON_DEACTIVATE("beacon.deactivate"),
    BEACON_POWER("beacon.power"),
    CONDUIT_ACTIVATE("conduit.activate"),
    CONDUIT_AMBIENT("conduit.ambient"),
    CONDUIT_ATTACK("conduit.attack"),
    CONDUIT_DEACTIVATE("conduit.deactivate"),
    CONDUIT_SHORT("conduit.short"),
    SWOOP("swoop"),
    BLOCK_BAMBOO_SAPLING_PLACE("block.bamboo_sapling.place"),
    PRE_SNEEZE("presneeze"),
    SNEEZE("sneeze"),
    AMBIENT_TAME("ambient.tame"),
    SCARED("scared"),
    BLOCK_SCAFFOLDING_CLIMB("block.scaffolding.climb"),
    CROSSBOW_LOADING_START("crossbow.loading.start"),
    CROSSBOW_LOADING_MIDDLE("crossbow.loading.middle"),
    CROSSBOW_LOADING_END("crossbow.loading.end"),
    CROSSBOW_SHOOT("crossbow.shoot"),
    CROSSBOW_QUICK_CHARGE_START("crossbow.quick_charge.start"),
    CROSSBOW_QUICK_CHARGE_MIDDLE("crossbow.quick_charge.middle"),
    CROSSBOW_QUICK_CHARGE_END("crossbow.quick_charge.end"),
    AMBIENT_AGGRESSIVE("ambient.aggressive"),
    AMBIENT_WORRIED("ambient.worried"),
    CANT_BREED("cant_breed"),
    SHIELD_BLOCK("item.shield.block"),
    LECTERN_BOOK_PLACE("item.book.put"),
    GRINDSTONE_USE("block.grindstone.use"),
    BELL("block.bell.hit"),
    CAMPFIRE_CRACKLE("block.campfire.crackle"),
    SWEET_BERRY_BUSH_HURT("block.sweet_berry_bush.hurt"),
    SWEET_BERRY_BUSH_PICK("block.sweet_berry_bush.pick"),
    ROAR("roar"),
    STUN("stun"),
    CARTOGRAPHY_TABLE_USE("block.cartography_table.use"),
    STONECUTTER_USE("block.stonecutter.use"),
    COMPOSTER_EMPTY("block.composter.empty"),
    COMPOSTER_FILL("block.composter.fill"),
    COMPOSTER_FILL_LAYER("block.composter.fill_success"),
    COMPOSTER_READY("block.composter.ready"),
    BARREL_OPEN("block.barrel.open"),
    BARREL_CLOSE("block.barrel.close"),
    RAID_HORN("raid.horn"),
    LOOM_USE("block.loom.use"),
    AMBIENT_IN_RAID("ambient.in.raid"),
    UI_CARTOGRAPHY_TABLE_USE("ui.cartography_table.take_result"),
    UI_STONECUTTER_USE("ui.stonecutter.take_result"),
    UI_LOOM_USE("ui.loom.take_result"),
    SMOKER_USE("block.smoker.smoke"),
    BLAST_FURNACE_USE("block.blastfurnace.fire_crackle"),
    SMITHING_TABLE_USE("block.smithing_table.use"),
    SCREECH("screech"),
    SLEEP("sleep"),
    FURNACE_USE("block.furnace.lit"),
    MOOSHROOM_CONVERT("convert_mooshroom"),
    MILK_SUSPICIOUSLY("milk_suspiciously"),
    CELEBRATE("celebrate"),
    JUMP_PREVENT("jump.prevent"),
    AMBIENT_POLLINATE("ambient.pollinate"),
    BEEHIVE_DRIP("block.beehive.drip"),
    BEEHIVE_ENTER("block.beehive.enter"),
    BEEHIVE_EXIT("block.beehive.exit"),
    BEEHIVE_WORK("block.beehive.work"),
    BEEHIVE_SHEAR("block.beehive.shear"),
    HONEYBOTTLE_DRINK("drink.honey"),
    AMBIENT_CAVE("ambient.cave"),
    RETREAT("retreat"),
    CONVERT_TO_ZOMBIFIED("converted_to_zombified"),
    ADMIRE("admire"),
    STEP_LAVA("step_lava"),
    TEMPT("tempt"),
    PANIC("panic"),
    ANGRY("angry"),
    AMBIENT_WARPED_FOREST("ambient.warped_forest.mood"),
    AMBIENT_SOULSAND_VALLEY("ambient.soulsand_valley.mood"),
    AMBIENT_NETHER_WASTES("ambient.nether_wastes.mood"),
    AMBIENT_BASALT_DELTAS("ambient.basalt_deltas.mood"),
    AMBIENT_CRIMSON_FOREST("ambient.crimson_forest.mood"),
    RESPAWN_ANCHOR_CHARGE("respawn_anchor.charge"),
    RESPAWN_ANCHOR_DEPLETE("respawn_anchor.deplete"),
    RESPAWN_ANCHOR_SET_SPAWN("respawn_anchor.set_spawn"),
    RESPAWN_ANCHOR_AMBIENT("respawn_anchor.ambient"),
    SOUL_ESCAPE_QUIET("particle.soul_escape.quiet"),
    SOUL_ESCAPE_LOUD("particle.soul_escape.loud"),
    RECORD_PIGSTEP("record.pigstep"),
    LINK_COMPASS_TO_LODESTONE("lodestone_compass.link_compass_to_lodestone"),
    USE_SMITHING_TABLE("smithing_table.use"),
    EQUIP_NETHERITE("armor.equip_netherite"),
    AMBIENT_LOOP_WARPED_FOREST("ambient.warped_forest.loop"),
    AMBIENT_LOOP_SOULSAND_VALLEY("ambient.soulsand_valley.loop"),
    AMBIENT_LOOP_NETHER_WASTES("ambient.nether_wastes.loop"),
    AMBIENT_LOOP_BASALT_DELTAS("ambient.basalt_deltas.loop"),
    AMBIENT_LOOP_CRIMSON_FOREST("ambient.crimson_forest.loop"),
    AMBIENT_ADDITION_WARPED_FOREST("ambient.warped_forest.additions"),
    AMBIENT_ADDITION_SOULSAND_VALLEY("ambient.soulsand_valley.additions"),
    AMBIENT_ADDITION_NETHER_WASTES("ambient.nether_wastes.additions"),
    AMBIENT_ADDITION_BASALT_DELTAS("ambient.basalt_deltas.additions"),
    AMBIENT_ADDITION_CRIMSON_FOREST("ambient.crimson_forest.additions"),
    SCULK_SENSOR_POWER_ON("power.on.sculk_sensor"),
    SCULK_SENSOR_POWER_OFF("power.off.sculk_sensor"),
    BUCKET_FILL_POWDER_SNOW("bucket.fill.powder_snow"),
    BUCKET_EMPTY_POWDER_SNOW("bucket.empty.powder_snow"),
    POINTED_DRIPSTONE_CAULDRON_DRIP_WATER("cauldron_drip.lava.pointed_dripstone"),
    POINTED_DRIPSTONE_CAULDRON_DRIP_LAVA("cauldron_drip.water.pointed_dripstone"),
    POINTED_DRIPSTONE_DRIP_WATER("drip.lava.pointed_dripstone"),
    POINTED_DRIPSTONE_DRIP_LAVA("drip.water.pointed_dripstone"),
    CAVE_VINES_PICK_BERRIES("pick_berries.cave_vines"),
    BIG_DRIPLEAF_TILT_DOWN("tilt_down.big_dripleaf"),
    BIG_DRIPLEAF_TILT_UP("tilt_up.big_dripleaf"),
    COPPER_WAX_ON("copper.wax.on"),
    COPPER_WAX_OFF("copper.wax.off"),
    SCRAPE("scrape"),
    PLAYER_HURT_DROWN("mob.player.hurt_drown"),
    PLAYER_HURT_ON_FIRE("mob.player.hurt_on_fire"),
    PLAYER_HURT_FREEZE("mob.player.hurt_freeze"),
    USE_SPYGLASS("item.spyglass.use"),
    STOP_USING_SPYGLASS("item.spyglass.stop_using"),
    AMETHYST_BLOCK_CHIME("chime.amethyst_block"),
    AMBIENT_SCREAMER("ambient.screamer"),
    HURT_SCREAMER("hurt.screamer"),
    DEATH_SCREAMER("death.screamer"),
    MILK_SCREAMER("milk.screamer"),
    JUMP_TO_BLOCK("jump_to_block"),
    PRE_RAM("pre_ram"),
    PRE_RAM_SCREAMER("pre_ram.screamer"),
    RAM_IMPACT("ram_impact"),
    RAM_IMPACT_SCREAMER("ram_impact.screamer"),
    SQUID_INK_SQUIRT("squid.ink_squirt"),
    GLOW_SQUID_INK_SQUIRT("glow_squid.ink_squirt"),
    CONVERT_TO_STRAY("convert_to_stray"),
    CAKE_ADD_CANDLE("cake.add_candle"),
    EXTINGUISH_CANDLE("extinguish.candle"),
    AMBIENT_CANDLE("ambient.candle"),
    BLOCK_CLICK("block.click"),
    BLOCK_CLICK_FAIL("block.click.fail"),
    SCULK_CATALYST_BLOOM("block.sculk_catalyst.bloom"),
    SCULK_SHRIEKER_SHRIEK("block.sculk_shrieker.shriek"),
    WARDEN_NEARBY_CLOSE("nearby_close"),
    WARDEN_NEARBY_CLOSER("nearby_closer"),
    WARDEN_NEARBY_CLOSEST("nearby_closest"),
    WARDEN_SLIGHTLY_ANGRY("agitated"),
    RECORD_OTHERSIDE("record.otherside"),
    TONGUE("tongue"),
    CRACK_IRON_GOLEM("irongolem.crack"),
    REPAIR_IRON_GOLEM("irongolem.repair"),
    LISTENING("listening"),
    HEARTBEAT("heartbeat"),
    HORN_BREAK("horn_break"),
    /**
     * @deprecated since v1001
     */
    SCULK_PLACE("undefined"),
    SCULK_SPREAD("block.sculk.spread"),
    SCULK_CHARGE("charge.sculk"),
    SCULK_SENSOR_PLACE("block.sculk_sensor.place"),
    SCULK_SHRIEKER_PLACE("block.sculk_shrieker.place"),
    GOAT_CALL_0("horn_call0"),
    GOAT_CALL_1("horn_call1"),
    GOAT_CALL_2("horn_call2"),
    GOAT_CALL_3("horn_call3"),
    GOAT_CALL_4("horn_call4"),
    GOAT_CALL_5("horn_call5"),
    GOAT_CALL_6("horn_call6"),
    GOAT_CALL_7("horn_call7"),
    /**
     * @deprecated since v1001
     */
    GOAT_CALL_8("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_CALL_9("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_0("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_1("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_2("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_3("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_4("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_5("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_6("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_7("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_8("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_HARMONY_9("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_0("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_1("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_2("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_3("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_4("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_5("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_6("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_7("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_8("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_MELODY_9("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_0("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_1("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_2("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_3("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_4("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_5("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_6("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_7("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_8("undefined"),
    /**
     * @deprecated since v1001
     */
    GOAT_BASS_9("undefined"),
    IMITATE_WARDEN("imitate.warden"),
    LISTENING_ANGRY("listening_angry"),
    ITEM_GIVEN("item_given"),
    ITEM_TAKEN("item_taken"),
    DISAPPEARED("disappeared"),
    REAPPEARED("reappeared"),
    FROGSPAWN_HATCHED("block.frog_spawn.hatch"),
    LAY_SPAWN("lay_spawn"),
    FROGSPAWN_BREAK("block.frog_spawn.break"),
    SONIC_BOOM("sonic_boom"),
    SONIC_CHARGE("sonic_charge"),
    ITEM_THROWN("item_thrown"),
    RECORD_5("record.5"),
    CONVERT_TO_FROG("convert_to_frog"),
    MILK_DRINK("drink.milk"),
    /**
     * @deprecated since v1001
     */
    RECORD_PLAYING("undefined"),
    ENCHANTING_TABLE_USE("block.enchanting_table.use"),
    BUNDLE_DROP_CONTENTS("bundle.drop_contents"),
    BUNDLE_INSERT("bundle.insert"),
    BUNDLE_REMOVE_ONE("bundle.remove_one"),
    /**
     * @since v560
     */
    PRESSURE_PLATE_CLICK_OFF("pressure_plate.click_off"),
    PRESSURE_PLATE_CLICK_ON("pressure_plate.click_on"),
    BUTTON_CLICK_OFF("button.click_off"),
    BUTTON_CLICK_ON("button.click_on"),
    DOOR_OPEN("door.open"),
    DOOR_CLOSE("door.close"),
    TRAPDOOR_OPEN("trapdoor.open"),
    TRAPDOOR_CLOSE("trapdoor.close"),
    FENCE_GATE_OPEN("fence_gate.open"),
    FENCE_GATE_CLOSE("fence_gate.close"),
    /**
     * @since v567
     */
    INSERT("insert"),
    /**
     * @since v567
     */
    PICKUP("pickup"),
    /**
     * @since v567
     */
    INSERT_ENCHANTED("insert_enchanted"),
    /**
     * @since v567
     */
    PICKUP_ENCHANTED("pickup_enchanted"),
    /**
     * @since v575
     */
    BRUSH("brush"),
    /**
     * @since v575
     */
    BRUSH_COMPLETED("brush_completed"),
    /**
     * @since v575
     */
    SHATTER_DECORATED_POT("shatter_pot"),
    /**
     * @since v575
     */
    BREAK_DECORATED_POD("break_pot"),
    /**
     * @since v589
     */
    SNIFFER_EGG_CRACK("block.sniffer_egg.crack"),
    /**
     * @since v589
     */
    SNIFFER_EGG_HATCHED("block.sniffer_egg.hatch"),
    /**
     * @since v589
     */
    WAXED_SIGN_INTERACT_FAIL("block.sign.waxed_interact_fail"),
    /**
     * @since v589
     */
    RECORD_RELIC("record.relic"),
    /**
     * @since v618
     */
    BUMP("note.bass"),
    /**
     * @since v618
     */
    PUMPKIN_CARVE("pumpkin.carve"),
    /**
     * @since v618
     */
    CONVERT_HUSK_TO_ZOMBIE("mob.husk.convert_to_zombie"),
    /**
     * @since v618
     */
    PIG_DEATH("mob.pig.death"),
    /**
     * @since v618
     */
    HOGLIN_CONVERT_TO_ZOMBIE("mob.hoglin.converted_to_zombified"),
    /**
     * @since v618
     */
    AMBIENT_UNDERWATER_ENTER("ambient.underwater.enter"),
    /**
     * @since v618
     */
    AMBIENT_UNDERWATER_EXIT("ambient.underwater.exit"),
    /**
     * @since v622
     */
    BOTTLE_FILL("bottle.fill"),
    /**
     * @since v622
     */
    BOTTLE_EMPTY("bottle.empty"),
    /**
     * @since v630
     */
    CRAFTER_CRAFT("crafter.craft"),
    /**
     * @since v630
     */
    CRAFTER_FAILED("crafter.fail"),
    /**
     * @since v630
     */
    CRAFTER_DISABLE_SLOT("crafter.disable_slot"),
    /**
     * @since v630
     */
    DECORATED_POT_INSERT("block.decorated_pot.insert"),
    /**
     * @since v630
     */
    DECORATED_POT_INSERT_FAILED("block.decorated_pot.insert_fail"),
    /**
     * @since v630
     */
    COPPER_BULB_ON("block.copper_bulb.turn_on"),
    /**
     * @since v630
     */
    COPPER_BULB_OFF("block.copper_bulb.turn_off"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_OPEN_SHUTTER("trial_spawner.open_shutter"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_EJECT_ITEM("trial_spawner.eject_item"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_DETECT_PLAYER("trial_spawner.detect_player"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_SPAWN_MOB("trial_spawner.spawn_mob"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_CLOSE_SHUTTER("trial_spawner.close_shutter"),
    /**
     * @since v630
     */
    TRIAL_SPAWNER_AMBIENT("trial_spawner.ambient"),
    /**
     * @since v649
     */
    AMBIENT_IN_AIR("ambient.in.air"),
    /**
     * @since v649
     */
    WIND_BURST("breeze_wind_charge.burst"),
    /**
     * @since v649
     */
    IMITATE_BREEZE("imitate.breeze"),
    /**
     * @since v649
     */
    ARMADILLO_BRUSH("mob.armadillo.brush"),
    /**
     * @since v649
     */
    ARMADILLO_SCUTE_DROP("mob.armadillo.scute_drop"),
    /**
     * @since v649
     */
    EQUIP_WOLF("armor.equip_wolf"),
    /**
     * @since v649
     */
    UNEQUIP_WOLF("armor.unequip_wolf"),
    /**
     * @since v649
     */
    REFLECT("reflect"),
    /**
     * @since v662
     */
    VAULT_OPEN_SHUTTER("vault.open_shutter"),
    /**
     * @since v662
     */
    VAULT_CLOSE_SHUTTER("vault.close_shutter"),
    /**
     * @since v662
     */
    VAULT_EJECT_ITEM("vault.eject_item"),
    /**
     * @since v662
     */
    VAULT_INSERT_ITEM("vault.insert_item"),
    /**
     * @since v662
     */
    VAULT_INSERT_ITEM_FAIL("vault.insert_item_fail"),
    /**
     * @since v662
     */
    VAULT_AMBIENT("vault.ambient"),
    /**
     * @since v662
     */
    VAULT_ACTIVATE("vault.activate"),
    /**
     * @since v662
     */
    VAULT_DEACTIVATE("vault.deactivate"),
    /**
     * @since v662
     */
    HURT_REDUCED("hurt.reduced"),
    /**
     * @since v662
     */
    WIND_CHARGE_BURST("wind_charge.burst"),
    /**
     * @since v671
     */
    ARMOR_CRACK_WOLF("armor.crack_wolf"),
    /**
     * @since v671
     */
    ARMOR_BREAK_WOLF("armor.break_wolf"),
    /**
     * @since v671
     */
    ARMOR_REPAIR_WOLF("armor.repair_wolf"),
    /**
     * @since v671
     */
    MACE_SMASH_AIR("mace.smash_air"),
    /**
     * @since v671
     */
    MACE_SMASH_GROUND("mace.smash_ground"),
    /**
     * @since v671
     */
    MACE_SMASH_HEAVY_GROUND("mace.heavy_smash_ground"),
    /**
     * @since v685
     */
    TRIAL_SPAWNER_CHARGE_ACTIVATE("trial_spawner.charge_activate"),
    /**
     * @since v685
     */
    TRIAL_SPAWNER_AMBIENT_OMINOUS("trial_spawner.ambient_ominous"),
    /**
     * @since v685
     */
    OMINOUS_ITEM_SPAWNER_SPAWN_ITEM("ominous_item_spawner.spawn_item"),
    /**
     * @since v685
     */
    OMINOUS_BOTTLE_END_USE("ominous_bottle.end_use"),
    /**
     * @since v685
     */
    OMINOUS_ITEM_SPAWNER_SPAWN_ITEM_BEGIN("ominous_item_spawner.spawn_item_begin"),
    /**
     * @since v685
     */
    APPLY_EFFECT_BAD_OMEN("apply_effect.bad_omen"),
    /**
     * @since v685
     */
    APPLY_EFFECT_RAID_OMEN("apply_effect.raid_omen"),
    /**
     * @since v685
     */
    APPLY_EFFECT_TRIAL_OMEN("apply_effect.trial_omen"),
    /**
     * @since v685
     */
    OMINOUS_ITEM_SPAWNER_ABOUT_TO_SPAWN_ITEM("ominous_item_spawner.about_to_spawn_item"),
    /**
     * @since v685
     */
    RECORD_CREATOR("record.creator"),
    /**
     * @since v685
     */
    RECORD_CREATOR_MUSIC_BOX("record.creator_music_box"),
    /**
     * @since v685
     */
    RECORD_PRECIPICE("record.precipice"),
    /**
     * @since v712
     */
    IMITATE_BOGGED("imitate.bogged"),
    /**
     * @since v712
     */
    VAULT_REJECT_REWARDED_PLAYER("vault.reject_rewarded_player"),
    /**
     * @since v729
     */
    IMITATE_DROWNED("imitate.drowned"),
    /**
     * @since v729
     */
    BUNDLE_INSERT_FAILED("bundle.insert_fail"),
    /**
     * @since v766
     */
    IMITATE_CREAKING("imitate.creaking"),
    /**
     * @since v766
     */
    SPONGE_ABSORB("sponge.absorb"),
    /**
     * @since v766
     */
    BLOCK_CREAKING_HEART_TRAIL("block.creaking_heart.trail"),
    /**
     * @since v766
     */
    CREAKING_HEART_SPAWN("creaking_heart_spawn"),
    /**
     * @since v766
     */
    ACTIVATE("activate"),
    /**
     * @since v766
     */
    DEACTIVATE("deactivate"),
    /**
     * @since v766
     */
    FREEZE("freeze"),
    /**
     * @since v766
     */
    UNFREEZE("unfreeze"),
    /**
     * @since v766
     */
    OPEN("open"),
    /**
     * @since v766
     */
    OPEN_LONG("open_long"),
    /**
     * @since v766
     */
    CLOSE("close"),
    /**
     * @since v766
     */
    CLOSE_LONG("close_long"),
    /**
     * @since v800
     */
    IMITATE_PHANTOM("imitate.phantom"),
    /**
     * @since v800
     */
    IMITATE_ZOGLIN("imitate.zoglin"),
    /**
     * @since v800
     */
    IMITATE_GUARDIAN("imitate.guardian"),
    /**
     * @since v800
     */
    IMITATE_RAVAGER("imitate.ravager"),
    /**
     * @since v800
     */
    IMITATE_PILLAGER("imitate.pillager"),
    /**
     * @since v800
     */
    PLACE_IN_WATER("place_in_water"),
    /**
     * @since v800
     */
    STATE_CHANGE("state_change"),
    /**
     * @since v800
     */
    IMITATE_HAPPY_GHAST("imitate.happy_ghast"),
    /**
     * @since v800
     */
    UNEQUIP_GENERIC("armor.unequip_generic"),
    /**
     * @since v818
     */
    RECORD_TEARS("record.tears"),
    /**
     * @since v818
     */
    THE_END_LIGHT_FLASH("ambient.weather.the_end_light_flash"),
    /**
     * @since v818
     */
    LEAD_LEASH("lead.leash"),
    /**
     * @since v818
     */
    LEAD_UNLEASH("lead.unleash"),
    /**
     * @since v818
     */
    LEAD_BREAK("lead.break"),
    /**
     * @since v818
     */
    UNSADDLE("unsaddle"),
    /**
     * @since v819
     */
    RECORD_LAVA_CHICKEN("record.lava_chicken"),
    /**
     * @since v827
     */
    EQUIP_COPPER("armor.equip_copper"),
    /**
     * @since v843
     */
    PLACE_ITEM("place_item"),
    /**
     * @since v843
     */
    SINGLE_ITEM_SWAP("single_swap"),
    /**
     * @since v843
     */
    MULTI_ITEM_SWAP("multi_swap"),
    /**
     * @since v897
     */
    LUNGE_1("item.enchant.lunge1"),
    /**
     * @since v897
     */
    LUNGE_2("item.enchant.lunge2"),
    /**
     * @since v897
     */
    LUNGE_3("item.enchant.lunge3"),
    /**
     * @since v897
     */
    ATTACK_CRITICAL("attack.critical"),
    /**
     * @since v897
     */
    SPEAR_ATTACK_HIT("item.spear.attack_hit"),
    /**
     * @since v897
     */
    SPEAR_ATTACK_MISS("item.spear.attack_miss"),
    /**
     * @since v897
     */
    WOODEN_SPEAR_ATTACK_HIT("item.wooden_spear.attack_hit"),
    /**
     * @since v897
     */
    WOODEN_SPEAR_ATTACK_MISS("item.wooden_spear.attack_miss"),
    /**
     * @since v897
     */
    IMITATE_PARCHED("imitate.parched"),
    /**
     * @since v897
     */
    IMITATE_CAMEL_HUSK("imitate.camel_husk"),
    /**
     * @since v897
     */
    SPEAR_USE("item.spear.use"),
    /**
     * @since v897
     */
    WOODEN_SPEAR_USE("item.wooden_spear.use"),
    /**
     * @since v924
     */
    SADDLE_IN_WATER("saddle_in_water"),
    /**
     * @since v924
     */
    STONE_SPEAR_ATTACK_HIT("item.stone_spear.attack_hit"),
    /**
     * @since v924
     */
    IRON_SPEAR_ATTACK_HIT("item.iron_spear.attack_hit"),
    /**
     * @since v924
     */
    COPPER_SPEAR_ATTACK_HIT("item.copper_spear.attack_hit"),
    /**
     * @since v924
     */
    GOLDEN_SPEAR_ATTACK_HIT("item.golden_spear.attack_hit"),
    /**
     * @since v924
     */
    DIAMOND_SPEAR_ATTACK_HIT("item.diamond_spear.attack_hit"),
    /**
     * @since v924
     */
    NETHERITE_SPEAR_ATTACK_HIT("item.netherite_spear.attack_hit"),
    /**
     * @since v924
     */
    STONE_SPEAR_ATTACK_MISS("item.stone_spear.attack_miss"),
    /**
     * @since v924
     */
    IRON_SPEAR_ATTACK_MISS("item.iron_spear.attack_miss"),
    /**
     * @since v924
     */
    COPPER_SPEAR_ATTACK_MISS("item.copper_spear.attack_miss"),
    /**
     * @since v924
     */
    GOLDEN_SPEAR_ATTACK_MISS("item.golden_spear.attack_miss"),
    /**
     * @since v924
     */
    DIAMOND_SPEAR_ATTACK_MISS("item.diamond_spear.attack_miss"),
    /**
     * @since v924
     */
    NETHERITE_SPEAR_ATTACK_MISS("item.netherite_spear.attack_miss"),
    /**
     * @since v924
     */
    STONE_SPEAR_USE("item.stone_spear.use"),
    /**
     * @since v924
     */
    IRON_SPEAR_USE("item.iron_spear.use"),
    /**
     * @since v924
     */
    COPPER_SPEAR_USE("item.copper_spear.use"),
    /**
     * @since v924
     */
    GOLDEN_SPEAR_USE("item.golden_spear.use"),
    /**
     * @since v924
     */
    DIAMOND_SPEAR_USE("item.diamond_spear.use"),
    /**
     * @since v924
     */
    NETHERITE_SPEAR_USE("item.netherite_spear.use"),
    /**
     * @since v944
     */
    PAUSE_GROWTH("pause_growth"),
    /**
     * @since v944
     */
    RESET_GROWTH("reset_growth"),
    /**
     * @since v975
     */
    PUSHED_BY_PLAYER("pushed_by_player"),
    /**
     * @since v975
     */
    BOUNCE("bounce"),
    /**
     * @since v1001
     */
    SLIME_LANDING("slime_landing"),
    /**
     * @since v1001
     */
    ABSORB_BLOCK("absorb_block"),
    /**
     * @since v1001
     */
    EJECT_BLOCK("eject_block"),
    /**
     * @since v1001
     */
    GEYSER_ERUPTION_START("geyser_eruption_start"),
    /**
     * @since v1001
     */
    GEYSER_ERUPTION_ACTIVE("geyser_eruption_active"),
    /**
     * @since v1001
     */
    RECORD_BOUNCE("record.bounce"),
    /**
     * @since v1001
     */
    BUCKET_FILL_LAND_ANIMAL("bucket.fill.land_animal"),
    /**
     * @since v1001
     */
    BUCKET_EMPTY_LAND_ANIMAL("bucket.empty.land_animal"),
    /**
     * @since v1001
     */
    GEYSER_CONTINUOUS_ERUPTION_START("geyser_continuous_eruption_start"),
    /**
     * @since v1001
     */
    GEYSER_CONTINUOUS_ERUPTION_ACTIVE("geyser_continuous_eruption_active"),
    /**
     * @since v2168
     */
    MOUNT("mount"),
    /**
     * @since v2168
     */
    DISMOUNT("dismount"),
    /**
     * @since v2168
     */
    STRAW_BED_BREAK_LEAVE("straw_bed.break_leave"),
    /**
     * @since v2225
     */
    ICE_BALL_BREAK("ice_ball.break"),
    UNDEFINED("undefined");

    private static final Map<String, SoundEvent> serializeNames = new HashMap<>(values().length, 1);
    static {
        for (SoundEvent value : values()) {
            serializeNames.put(value.getSerializeName(), value);
        }
    }

    private final String serializeName;

    SoundEvent(String serializeName) {
        this.serializeName = serializeName;
    }

    public String getSerializeName() {
        return this.serializeName;
    }

    public static SoundEvent fromName(String serializeName) {
        return serializeNames.get(serializeName);
    }
}
