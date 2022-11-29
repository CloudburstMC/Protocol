package com.nukkitx.protocol.bedrock.v560;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.v557.BedrockPacketHelper_v557;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v560 extends BedrockPacketHelper_v557 {

    public static final BedrockPacketHelper_v560 INSTANCE = new BedrockPacketHelper_v560();

    @Override
    protected void registerContainerSlotTypes() {
        this.containerSlotTypes.put(0, ContainerSlotType.ANVIL_INPUT);
        this.containerSlotTypes.put(1, ContainerSlotType.ANVIL_MATERIAL);
        this.containerSlotTypes.put(2, ContainerSlotType.ANVIL_RESULT);
        this.containerSlotTypes.put(3, ContainerSlotType.SMITHING_TABLE_INPUT);
        this.containerSlotTypes.put(4, ContainerSlotType.SMITHING_TABLE_MATERIAL);
        this.containerSlotTypes.put(5, ContainerSlotType.SMITHING_TABLE_RESULT);
        this.containerSlotTypes.put(6, ContainerSlotType.ARMOR);
        this.containerSlotTypes.put(7, ContainerSlotType.CONTAINER);
        this.containerSlotTypes.put(8, ContainerSlotType.BEACON_PAYMENT);
        this.containerSlotTypes.put(9, ContainerSlotType.BREWING_INPUT);
        this.containerSlotTypes.put(10, ContainerSlotType.BREWING_RESULT);
        this.containerSlotTypes.put(11, ContainerSlotType.BREWING_FUEL);
        this.containerSlotTypes.put(12, ContainerSlotType.HOTBAR_AND_INVENTORY);
        this.containerSlotTypes.put(13, ContainerSlotType.CRAFTING_INPUT);
        this.containerSlotTypes.put(14, ContainerSlotType.CRAFTING_OUTPUT);
        this.containerSlotTypes.put(15, ContainerSlotType.RECIPE_CONSTRUCTION);
        this.containerSlotTypes.put(16, ContainerSlotType.RECIPE_NATURE);
        this.containerSlotTypes.put(17, ContainerSlotType.RECIPE_ITEMS);
        this.containerSlotTypes.put(18, ContainerSlotType.RECIPE_SEARCH);
        this.containerSlotTypes.put(19, ContainerSlotType.RECIPE_SEARCH_BAR);
        this.containerSlotTypes.put(20, ContainerSlotType.RECIPE_EQUIPMENT);
        this.containerSlotTypes.put(21, ContainerSlotType.RECIPE_BOOK);
        this.containerSlotTypes.put(22, ContainerSlotType.ENCHANTING_INPUT);
        this.containerSlotTypes.put(23, ContainerSlotType.ENCHANTING_LAPIS);
        this.containerSlotTypes.put(24, ContainerSlotType.FURNACE_FUEL);
        this.containerSlotTypes.put(25, ContainerSlotType.FURNACE_INGREDIENT);
        this.containerSlotTypes.put(26, ContainerSlotType.FURNACE_OUTPUT);
        this.containerSlotTypes.put(27, ContainerSlotType.HORSE_EQUIP);
        this.containerSlotTypes.put(28, ContainerSlotType.HOTBAR);
        this.containerSlotTypes.put(29, ContainerSlotType.INVENTORY);
        this.containerSlotTypes.put(30, ContainerSlotType.SHULKER);
        this.containerSlotTypes.put(31, ContainerSlotType.TRADE_INGREDIENT1);
        this.containerSlotTypes.put(32, ContainerSlotType.TRADE_INGREDIENT2);
        this.containerSlotTypes.put(33, ContainerSlotType.TRADE_RESULT);
        this.containerSlotTypes.put(34, ContainerSlotType.OFFHAND);
        this.containerSlotTypes.put(35, ContainerSlotType.COMPCREATE_INPUT);
        this.containerSlotTypes.put(36, ContainerSlotType.COMPCREATE_OUTPUT);
        this.containerSlotTypes.put(37, ContainerSlotType.ELEMCONSTRUCT_OUTPUT);
        this.containerSlotTypes.put(38, ContainerSlotType.MATREDUCE_INPUT);
        this.containerSlotTypes.put(39, ContainerSlotType.MATREDUCE_OUTPUT);
        this.containerSlotTypes.put(40, ContainerSlotType.LABTABLE_INPUT);
        this.containerSlotTypes.put(41, ContainerSlotType.LOOM_INPUT);
        this.containerSlotTypes.put(42, ContainerSlotType.LOOM_DYE);
        this.containerSlotTypes.put(43, ContainerSlotType.LOOM_MATERIAL);
        this.containerSlotTypes.put(44, ContainerSlotType.LOOM_RESULT);
        this.containerSlotTypes.put(45, ContainerSlotType.BLAST_FURNACE_INGREDIENT);
        this.containerSlotTypes.put(46, ContainerSlotType.SMOKER_INGREDIENT);
        this.containerSlotTypes.put(47, ContainerSlotType.TRADE2_INGREDIENT1);
        this.containerSlotTypes.put(48, ContainerSlotType.TRADE2_INGREDIENT2);
        this.containerSlotTypes.put(49, ContainerSlotType.TRADE2_RESULT);
        this.containerSlotTypes.put(50, ContainerSlotType.GRINDSTONE_INPUT);
        this.containerSlotTypes.put(51, ContainerSlotType.GRINDSTONE_ADDITIONAL);
        this.containerSlotTypes.put(52, ContainerSlotType.GRINDSTONE_RESULT);
        this.containerSlotTypes.put(53, ContainerSlotType.STONECUTTER_INPUT);
        this.containerSlotTypes.put(54, ContainerSlotType.STONECUTTER_RESULT);
        this.containerSlotTypes.put(55, ContainerSlotType.CARTOGRAPHY_INPUT);
        this.containerSlotTypes.put(56, ContainerSlotType.CARTOGRAPHY_ADDITIONAL);
        this.containerSlotTypes.put(57, ContainerSlotType.CARTOGRAPHY_RESULT);
        this.containerSlotTypes.put(58, ContainerSlotType.BARREL);
        this.containerSlotTypes.put(59, ContainerSlotType.CURSOR);
        this.containerSlotTypes.put(60, ContainerSlotType.CREATIVE_OUTPUT);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(46, EntityFlag.CAN_DASH); // v560
        this.addEntityFlag(47, EntityFlag.LINGERING);
        this.addEntityFlag(49, EntityFlag.HAS_COLLISION);
        this.addEntityFlag(49, EntityFlag.HAS_GRAVITY);
        this.addEntityFlag(50, EntityFlag.FIRE_IMMUNE);
        this.addEntityFlag(51, EntityFlag.DANCING);
        this.addEntityFlag(52, EntityFlag.ENCHANTED);
        this.addEntityFlag(53, EntityFlag.RETURN_TRIDENT);
        this.addEntityFlag(54, EntityFlag.CONTAINER_IS_PRIVATE);
        this.addEntityFlag(55, EntityFlag.IS_TRANSFORMING);
        this.addEntityFlag(56, EntityFlag.DAMAGE_NEARBY_MOBS);
        this.addEntityFlag(57, EntityFlag.SWIMMING);
        this.addEntityFlag(58, EntityFlag.BRIBED);
        this.addEntityFlag(59, EntityFlag.IS_PREGNANT);
        this.addEntityFlag(60, EntityFlag.LAYING_EGG);
        this.addEntityFlag(61, EntityFlag.RIDER_CAN_PICK);
        this.addEntityFlag(62, EntityFlag.TRANSITION_SITTING);
        this.addEntityFlag(63, EntityFlag.EATING);
        this.addEntityFlag(64, EntityFlag.LAYING_DOWN);
        this.addEntityFlag(65, EntityFlag.SNEEZING);
        this.addEntityFlag(66, EntityFlag.TRUSTING);
        this.addEntityFlag(67, EntityFlag.ROLLING);
        this.addEntityFlag(68, EntityFlag.SCARED);
        this.addEntityFlag(69, EntityFlag.IN_SCAFFOLDING);
        this.addEntityFlag(70, EntityFlag.OVER_SCAFFOLDING);
        this.addEntityFlag(71, EntityFlag.FALL_THROUGH_SCAFFOLDING);
        this.addEntityFlag(72, EntityFlag.BLOCKING);
        this.addEntityFlag(73, EntityFlag.TRANSITION_BLOCKING);
        this.addEntityFlag(74, EntityFlag.BLOCKED_USING_SHIELD);
        this.addEntityFlag(75, EntityFlag.BLOCKED_USING_DAMAGED_SHIELD);
        this.addEntityFlag(76, EntityFlag.SLEEPING);
        this.addEntityFlag(77, EntityFlag.WANTS_TO_WAKE);
        this.addEntityFlag(78, EntityFlag.TRADE_INTEREST);
        this.addEntityFlag(79, EntityFlag.DOOR_BREAKER);
        this.addEntityFlag(80, EntityFlag.BREAKING_OBSTRUCTION);
        this.addEntityFlag(81, EntityFlag.DOOR_OPENER);
        this.addEntityFlag(82, EntityFlag.IS_ILLAGER_CAPTAIN);
        this.addEntityFlag(83, EntityFlag.STUNNED);
        this.addEntityFlag(84, EntityFlag.ROARING);
        this.addEntityFlag(85, EntityFlag.DELAYED_ATTACK);
        this.addEntityFlag(86, EntityFlag.IS_AVOIDING_MOBS);
        this.addEntityFlag(87, EntityFlag.IS_AVOIDING_BLOCK);
        this.addEntityFlag(88, EntityFlag.FACING_TARGET_TO_RANGE_ATTACK);
        this.addEntityFlag(89, EntityFlag.HIDDEN_WHEN_INVISIBLE);
        this.addEntityFlag(90, EntityFlag.IS_IN_UI);
        this.addEntityFlag(91, EntityFlag.STALKING);
        this.addEntityFlag(92, EntityFlag.EMOTING);
        this.addEntityFlag(93, EntityFlag.CELEBRATING);
        this.addEntityFlag(94, EntityFlag.ADMIRING);
        this.addEntityFlag(95, EntityFlag.CELEBRATING_SPECIAL);
        this.addEntityFlag(96, EntityFlag.OUT_OF_CONTROL);
        this.addEntityFlag(97, EntityFlag.RAM_ATTACK);
        this.addEntityFlag(98, EntityFlag.PLAYING_DEAD);
        this.addEntityFlag(99, EntityFlag.IN_ASCENDABLE_BLOCK);
        this.addEntityFlag(100, EntityFlag.OVER_DESCENDABLE_BLOCK);
        this.addEntityFlag(101, EntityFlag.CROAKING);
        this.addEntityFlag(102, EntityFlag.EAT_MOB);
        this.addEntityFlag(103, EntityFlag.JUMP_GOAL_JUMP);
        this.addEntityFlag(104, EntityFlag.EMERGING);
        this.addEntityFlag(105, EntityFlag.SNIFFING);
        this.addEntityFlag(106, EntityFlag.DIGGING);
        this.addEntityFlag(107, EntityFlag.SONIC_BOOM);
        this.addEntityFlag(108, EntityFlag.HAS_DASH_COOLDOWN); // v560
        this.addEntityFlag(109, EntityFlag.PUSH_TOWARDS_CLOSEST_SPACE); // v560
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.soundEvents.remove(448); // Remove old undefined value
        this.soundEvents.put(448, SoundEvent.PRESSURE_PLATE_CLICK_OFF);
        this.soundEvents.put(449, SoundEvent.PRESSURE_PLATE_CLICK_ON);
        this.soundEvents.put(450, SoundEvent.BUTTON_CLICK_OFF);
        this.soundEvents.put(451, SoundEvent.BUTTON_CLICK_ON);
        this.soundEvents.put(452, SoundEvent.DOOR_OPEN);
        this.soundEvents.put(453, SoundEvent.DOOR_CLOSE);
        this.soundEvents.put(454, SoundEvent.TRAPDOOR_OPEN);
        this.soundEvents.put(455, SoundEvent.TRAPDOOR_CLOSE);
        this.soundEvents.put(456, SoundEvent.FENCE_GATE_OPEN);
        this.soundEvents.put(457, SoundEvent.FENCE_GATE_CLOSE);
        this.soundEvents.put(458, SoundEvent.UNDEFINED);
    }
}
