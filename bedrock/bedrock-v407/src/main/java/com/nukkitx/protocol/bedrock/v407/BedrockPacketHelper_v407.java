package com.nukkitx.protocol.bedrock.v407;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.*;
import com.nukkitx.protocol.bedrock.v390.BedrockPacketHelper_v390;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;

public class BedrockPacketHelper_v407 extends BedrockPacketHelper_v390 {
    public static final BedrockPacketHelper_v407 INSTANCE = new BedrockPacketHelper_v407();

    protected final Int2ObjectBiMap<StackRequestActionType> stackRequestActionTypes = new Int2ObjectBiMap<>();

    protected BedrockPacketHelper_v407() {
        super();

        this.registerStackActionRequestTypes();
    }

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(113, EntityData.LOW_TIER_CURED_TRADE_DISCOUNT);
        this.addEntityData(114, EntityData.HIGH_TIER_CURED_TRADE_DISCOUNT);
        this.addEntityData(115, EntityData.NEARBY_CURED_TRADE_DISCOUNT);
        this.addEntityData(116, EntityData.NEARBY_CURED_DISCOUNT_TIME_STAMP);
        this.addEntityData(117, EntityData.HITBOX);
        this.addEntityData(118, EntityData.IS_BUOYANT);
        this.addEntityData(119, EntityData.BUOYANCY_DATA);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(86, EntityFlag.IS_AVOIDING_BLOCK);
        this.addEntityFlag(87, EntityFlag.FACING_TARGET_TO_RANGE_ATTACK);
        this.addEntityFlag(88, EntityFlag.HIDDEN_WHEN_INVISIBLE);
        this.addEntityFlag(89, EntityFlag.IS_IN_UI);
        this.addEntityFlag(90, EntityFlag.STALKING);
        this.addEntityFlag(91, EntityFlag.EMOTING);
        this.addEntityFlag(92, EntityFlag.CELEBRATING);
        this.addEntityFlag(93, EntityFlag.ADMIRING);
        this.addEntityFlag(94, EntityFlag.CELEBRATING_SPECIAL);
    }

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();

        this.addEntityEvent(75, EntityEventType.LANDED_ON_GROUND);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(1050, LevelEventType.SOUND_CAMERA);

        this.addLevelEvent(3600, LevelEventType.BLOCK_START_BREAK);
        this.addLevelEvent(3601, LevelEventType.BLOCK_STOP_BREAK);
        this.addLevelEvent(3602, LevelEventType.BLOCK_UPDATE_BREAK);

        this.addLevelEvent(4000, LevelEventType.SET_DATA);

        this.addLevelEvent(9800, LevelEventType.ALL_PLAYERS_SLEEPING);

        int legacy = 0x4000;
        this.addLevelEvent(68 + legacy, LevelEventType.PARTICLE_BLUE_FLAME);
        this.addLevelEvent(69 + legacy, LevelEventType.PARTICLE_SOUL);
        this.addLevelEvent(70 + legacy, LevelEventType.PARTICLE_OBSIDIAN_TEAR);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(287, SoundEvent.JUMP_PREVENT);
        this.addSoundEvent(288, SoundEvent.AMBIENT_POLLINATE);
        this.addSoundEvent(289, SoundEvent.BEEHIVE_DRIP);
        this.addSoundEvent(290, SoundEvent.BEEHIVE_ENTER);
        this.addSoundEvent(291, SoundEvent.BEEHIVE_EXIT);
        this.addSoundEvent(292, SoundEvent.BEEHIVE_WORK);
        this.addSoundEvent(293, SoundEvent.BEEHIVE_SHEAR);
        this.addSoundEvent(294, SoundEvent.HONEYBOTTLE_DRINK);
        this.addSoundEvent(295, SoundEvent.AMBIENT_CAVE);
        this.addSoundEvent(296, SoundEvent.RETREAT);
        this.addSoundEvent(297, SoundEvent.CONVERT_TO_ZOMBIFIED);
        this.addSoundEvent(298, SoundEvent.ADMIRE);
        this.addSoundEvent(299, SoundEvent.STEP_LAVA);
        this.addSoundEvent(300, SoundEvent.TEMPT);
        this.addSoundEvent(301, SoundEvent.PANIC);
        this.addSoundEvent(302, SoundEvent.ANGRY);
        this.addSoundEvent(303, SoundEvent.AMBIENT_WARPED_FOREST);
        this.addSoundEvent(304, SoundEvent.AMBIENT_SOULSAND_VALLEY);
        this.addSoundEvent(305, SoundEvent.AMBIENT_NETHER_WASTES);
        this.addSoundEvent(306, SoundEvent.AMBIENT_BASALT_DELTAS);
        this.addSoundEvent(307, SoundEvent.AMBIENT_CRIMSON_FOREST);
        this.addSoundEvent(308, SoundEvent.RESPAWN_ANCHOR_CHARGE);
        this.addSoundEvent(309, SoundEvent.RESPAWN_ANCHOR_DEPLETE);
        this.addSoundEvent(310, SoundEvent.RESPAWN_ANCHOR_SET_SPAWN);
        this.addSoundEvent(311, SoundEvent.RESPAWN_ANCHOR_AMBIENT);
        this.addSoundEvent(312, SoundEvent.SOUL_ESCAPE_QUIET);
        this.addSoundEvent(313, SoundEvent.SOUL_ESCAPE_LOUD);
        this.addSoundEvent(314, SoundEvent.RECORD_PIGSTEP);
        this.addSoundEvent(315, SoundEvent.LINK_COMPASS_TO_LODESTONE);
        this.addSoundEvent(316, SoundEvent.USE_SMITHING_TABLE);
    }

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
        this.containerSlotTypes.put(21, ContainerSlotType.ENCHANTING_INPUT);
        this.containerSlotTypes.put(22, ContainerSlotType.ENCHANTING_LAPIS);
        this.containerSlotTypes.put(23, ContainerSlotType.FURNACE_FUEL);
        this.containerSlotTypes.put(24, ContainerSlotType.FURNACE_INGREDIENT);
        this.containerSlotTypes.put(25, ContainerSlotType.FURNACE_OUTPUT);
        this.containerSlotTypes.put(26, ContainerSlotType.HORSE_EQUIP);
        this.containerSlotTypes.put(27, ContainerSlotType.HOTBAR);
        this.containerSlotTypes.put(28, ContainerSlotType.INVENTORY);
        this.containerSlotTypes.put(29, ContainerSlotType.SHULKER);
        this.containerSlotTypes.put(30, ContainerSlotType.TRADE_INGREDIENT1);
        this.containerSlotTypes.put(31, ContainerSlotType.TRADE_INGREDIENT2);
        this.containerSlotTypes.put(32, ContainerSlotType.TRADE_RESULT);
        this.containerSlotTypes.put(33, ContainerSlotType.OFFHAND);
        this.containerSlotTypes.put(34, ContainerSlotType.COMPCREATE_INPUT);
        this.containerSlotTypes.put(35, ContainerSlotType.COMPCREATE_OUTPUT);
        this.containerSlotTypes.put(36, ContainerSlotType.ELEMCONSTRUCT_OUTPUT);
        this.containerSlotTypes.put(37, ContainerSlotType.MATREDUCE_INPUT);
        this.containerSlotTypes.put(38, ContainerSlotType.MATREDUCE_OUTPUT);
        this.containerSlotTypes.put(39, ContainerSlotType.LABTABLE_INPUT);
        this.containerSlotTypes.put(40, ContainerSlotType.LOOM_INPUT);
        this.containerSlotTypes.put(41, ContainerSlotType.LOOM_DYE);
        this.containerSlotTypes.put(42, ContainerSlotType.LOOM_MATERIAL);
        this.containerSlotTypes.put(43, ContainerSlotType.LOOM_RESULT);
        this.containerSlotTypes.put(44, ContainerSlotType.BLAST_FURNACE_INGREDIENT);
        this.containerSlotTypes.put(45, ContainerSlotType.SMOKER_INGREDIENT);
        this.containerSlotTypes.put(46, ContainerSlotType.TRADE2_INGREDIENT1);
        this.containerSlotTypes.put(47, ContainerSlotType.TRADE2_INGREDIENT2);
        this.containerSlotTypes.put(48, ContainerSlotType.TRADE2_RESULT);
        this.containerSlotTypes.put(49, ContainerSlotType.GRINDSTONE_INPUT);
        this.containerSlotTypes.put(50, ContainerSlotType.GRINDSTONE_ADDITIONAL);
        this.containerSlotTypes.put(51, ContainerSlotType.GRINDSTONE_RESULT);
        this.containerSlotTypes.put(52, ContainerSlotType.STONECUTTER_INPUT);
        this.containerSlotTypes.put(53, ContainerSlotType.STONECUTTER_RESULT);
        this.containerSlotTypes.put(54, ContainerSlotType.CARTOGRAPHY_INPUT);
        this.containerSlotTypes.put(55, ContainerSlotType.CARTOGRAPHY_ADDITIONAL);
        this.containerSlotTypes.put(56, ContainerSlotType.CARTOGRAPHY_RESULT);
        this.containerSlotTypes.put(57, ContainerSlotType.BARREL);
        this.containerSlotTypes.put(58, ContainerSlotType.CURSOR);
        this.containerSlotTypes.put(59, ContainerSlotType.CREATIVE_OUTPUT);
    }

    @Override
    public ContainerSlotType readContainerSlotType(ByteBuf buffer) {
        return this.containerSlotTypes.get(buffer.readByte());
    }

    @Override
    public void writeContainerSlotType(ByteBuf buffer, ContainerSlotType slotType) {
        buffer.writeByte(this.containerSlotTypes.get(slotType));
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        return new EntityLinkData(
                VarInts.readLong(buffer),
                VarInts.readLong(buffer),
                EntityLinkData.Type.byId(buffer.readUnsignedByte()),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData entityLink) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityLink, "entityLink");

        VarInts.writeLong(buffer, entityLink.getFrom());
        VarInts.writeLong(buffer, entityLink.getTo());
        buffer.writeByte(entityLink.getType().ordinal());
        buffer.writeBoolean(entityLink.isImmediate());
        buffer.writeBoolean(entityLink.isRiderInitiated());
    }

    @Override
    public boolean readInventoryActions(ByteBuf buffer, BedrockSession session, List<InventoryActionData> actions) {
        boolean hasNetworkIds = buffer.readBoolean();
        this.readArray(buffer, actions, session, (buf, helper, aSession) -> {
            InventorySource source = helper.readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = helper.readItem(buf, aSession);
            ItemData toItem = helper.readItem(buf, aSession);
            int networkStackId = 0;
            if (hasNetworkIds) {
                networkStackId = VarInts.readInt(buf);
            }

            return new InventoryActionData(source, slot, fromItem, toItem, networkStackId);
        });
        return hasNetworkIds;
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, BedrockSession session, List<InventoryActionData> actions, boolean hasNetworkIds) {
        buffer.writeBoolean(hasNetworkIds);
        this.writeArray(buffer, actions, session, (buf, helper, aSession, action) -> {
            helper.writeSource(buffer, action.getSource());
            VarInts.writeUnsignedInt(buffer, action.getSlot());
            helper.writeItem(buffer, action.getFromItem(), aSession);
            helper.writeItem(buffer, action.getToItem(), aSession);
            if (hasNetworkIds) {
                VarInts.writeInt(buffer, action.getStackNetworkId());
            }
        });
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer, BedrockSession session) {
        int netId = VarInts.readInt(buffer);
        ItemData item = this.readItem(buffer, session);
        item.setNetId(netId);
        return item;
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item, BedrockSession session) {
        VarInts.writeInt(buffer, item.getNetId());
        this.writeItem(buffer, item, session);
    }

    @Override
    protected void registerStackActionRequestTypes() {
        this.stackRequestActionTypes.put(0, TAKE);
        this.stackRequestActionTypes.put(1, PLACE);
        this.stackRequestActionTypes.put(2, SWAP);
        this.stackRequestActionTypes.put(3, DROP);
        this.stackRequestActionTypes.put(4, DESTROY);
        this.stackRequestActionTypes.put(5, CONSUME);
        this.stackRequestActionTypes.put(6, CREATE);
        this.stackRequestActionTypes.put(7, LAB_TABLE_COMBINE);
        this.stackRequestActionTypes.put(8, BEACON_PAYMENT);
        this.stackRequestActionTypes.put(9, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(10, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(11, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(12, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(13, CRAFT_RESULTS_DEPRECATED);
    }

    @Override
    public StackRequestActionType getStackRequestActionTypeFromId(int id) {
        return this.stackRequestActionTypes.get(id);
    }

    @Override
    public int getIdFromStackRequestActionType(StackRequestActionType type) {
        return this.stackRequestActionTypes.get(type);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer, BedrockSession session) {
        int requestId = VarInts.readInt(buffer);
        List<StackRequestActionData> actions = new ArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            StackRequestActionType type = getStackRequestActionTypeFromId(byteBuf.readByte());
            return readRequestActionData(byteBuf, type, session);
        });
        return new ItemStackRequest(requestId, actions.toArray(new StackRequestActionData[0]), new String[0]);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, BedrockSession session, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.getRequestId());

        this.writeArray(buffer, request.getActions(), (byteBuf, action) -> {
            StackRequestActionType type = action.getType();
            byteBuf.writeByte(getIdFromStackRequestActionType(type));
            writeRequestActionData(byteBuf, action, session);
        });
    }

    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockSession session) {
        switch (action.getType()) {
            case TAKE:
            case PLACE:
                byteBuf.writeByte(((TransferStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((TransferStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((TransferStackRequestActionData) action).getDestination());
                break;
            case SWAP:
                writeStackRequestSlotInfo(byteBuf, ((SwapStackRequestActionData) action).getSource());
                writeStackRequestSlotInfo(byteBuf, ((SwapStackRequestActionData) action).getDestination());
                break;
            case DROP:
                byteBuf.writeByte(((DropStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DropStackRequestActionData) action).getSource());
                byteBuf.writeBoolean(((DropStackRequestActionData) action).isRandomly());
                break;
            case DESTROY:
                byteBuf.writeByte(((DestroyStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((DestroyStackRequestActionData) action).getSource());
                break;
            case CONSUME:
                byteBuf.writeByte(((ConsumeStackRequestActionData) action).getCount());
                writeStackRequestSlotInfo(byteBuf, ((ConsumeStackRequestActionData) action).getSource());
                break;
            case CREATE:
                byteBuf.writeByte(((CreateStackRequestActionData) action).getSlot());
                break;
            case LAB_TABLE_COMBINE:
                break;
            case BEACON_PAYMENT:
                VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getPrimaryEffect());
                VarInts.writeInt(byteBuf, ((BeaconPaymentStackRequestActionData) action).getSecondaryEffect());
                break;
            case CRAFT_RECIPE:
            case CRAFT_RECIPE_AUTO:
                VarInts.writeUnsignedInt(byteBuf, ((RecipeStackRequestActionData) action).getRecipeNetworkId());
                break;
            case CRAFT_CREATIVE:
                VarInts.writeUnsignedInt(byteBuf, ((CraftCreativeStackRequestActionData) action).getCreativeItemNetworkId());
                break;
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                break;
            case CRAFT_RESULTS_DEPRECATED:
                this.writeArray(byteBuf, ((CraftResultsDeprecatedStackRequestActionData) action).getResultItems(), (buf2, item) -> this.writeItem(buf2, item, session));
                byteBuf.writeByte(((CraftResultsDeprecatedStackRequestActionData) action).getTimesCrafted());
                break;
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + action.getType());
        }
    }

    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockSession session) {
        switch (type) {
            case TAKE:
                return new TakeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case PLACE:
                return new PlaceStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case SWAP:
                return new SwapStackRequestActionData(
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case DROP:
                return new DropStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf),
                        byteBuf.readBoolean()
                );
            case DESTROY:
                return new DestroyStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CONSUME:
                return new ConsumeStackRequestActionData(
                        byteBuf.readByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CREATE:
                return new CreateStackRequestActionData(
                        byteBuf.readByte()
                );
            case LAB_TABLE_COMBINE:
                return new LabTableCombineRequestActionData();
            case BEACON_PAYMENT:
                return new BeaconPaymentStackRequestActionData(
                        VarInts.readInt(byteBuf),
                        VarInts.readInt(byteBuf)
                );
            case CRAFT_RECIPE:
                return new CraftRecipeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_RECIPE_AUTO:
                return new AutoCraftRecipeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf), (byte) 0, Collections.emptyList()
                );
            case CRAFT_CREATIVE:
                return new CraftCreativeStackRequestActionData(
                        VarInts.readUnsignedInt(byteBuf)
                );
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                return new CraftNonImplementedStackRequestActionData();
            case CRAFT_RESULTS_DEPRECATED:
                return new CraftResultsDeprecatedStackRequestActionData(
                        this.readArray(byteBuf, new ItemData[0], buf2 -> this.readItem(buf2, session)),
                        byteBuf.readByte()
                );
            default:
                throw new UnsupportedOperationException("Unhandled stack request action type: " + type);
        }
    }

    protected StackRequestSlotInfoData readStackRequestSlotInfo(ByteBuf buffer) {
        return new StackRequestSlotInfoData(
                readContainerSlotType(buffer),
                buffer.readByte(),
                VarInts.readInt(buffer)
        );
    }

    protected void writeStackRequestSlotInfo(ByteBuf buffer, StackRequestSlotInfoData data) {
        writeContainerSlotType(buffer, data.getContainer());
        buffer.writeByte(data.getSlot());
        VarInts.writeInt(buffer, data.getStackNetworkId());
    }
}
