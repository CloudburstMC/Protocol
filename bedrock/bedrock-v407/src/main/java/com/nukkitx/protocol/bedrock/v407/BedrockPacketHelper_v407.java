package com.nukkitx.protocol.bedrock.v407;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.v390.BedrockPacketHelper_v390;
import com.nukkitx.protocol.util.Int2ObjectBiMap;
import io.netty.buffer.ByteBuf;

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
}
