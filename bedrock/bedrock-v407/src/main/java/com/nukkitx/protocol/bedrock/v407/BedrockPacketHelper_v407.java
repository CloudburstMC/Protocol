package com.nukkitx.protocol.bedrock.v407;

import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityLinkData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.v390.BedrockPacketHelper_v390;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.command.CommandParamType.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v407 extends BedrockPacketHelper_v390 {
    public static final BedrockPacketHelper_v407 INSTANCE = new BedrockPacketHelper_v407();

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
    protected void registerCommandParams() {
        this.addCommandParam(1, INT);
        this.addCommandParam(2, FLOAT);
        this.addCommandParam(3, VALUE);
        this.addCommandParam(4, WILDCARD_INT);
        this.addCommandParam(5, OPERATOR);
        this.addCommandParam(6, TARGET);
        this.addCommandParam(7, WILDCARD_TARGET);
        this.addCommandParam(14, FILE_PATH);
        this.addCommandParam(29, STRING);
        this.addCommandParam(37, BLOCK_POSITION);
        this.addCommandParam(38, POSITION);
        this.addCommandParam(41, MESSAGE);
        this.addCommandParam(43, TEXT);
        this.addCommandParam(47, JSON);
        this.addCommandParam(54, COMMAND);
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
    public ItemData readNetItem(ByteBuf buffer) {
        int netId = VarInts.readInt(buffer);
        ItemData item = this.readItem(buffer);
        item.setNetId(netId);
        return item;
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        VarInts.writeInt(buffer, item.getNetId());
        this.writeItem(buffer, item);
    }
}
