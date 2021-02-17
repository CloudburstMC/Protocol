package com.nukkitx.protocol.bedrock.v313;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.v291.BedrockPacketHelper_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.SoundEvent.*;
import static com.nukkitx.protocol.bedrock.data.entity.EntityData.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v313 extends BedrockPacketHelper_v291 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v313();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(88, SITTING_AMOUNT);
        this.addEntityData(89, SITTING_AMOUNT_PREVIOUS);
        this.addEntityData(90, EATING_COUNTER);
        this.addEntityData(91, FLAGS_2);
        this.addEntityData(92, LAYING_AMOUNT);
        this.addEntityData(93, LAYING_AMOUNT_PREVIOUS);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(61, EntityFlag.TRANSITION_SITTING);
        this.addEntityFlag(62, EntityFlag.EATING);
        this.addEntityFlag(63, EntityFlag.LAYING_DOWN);
        this.addEntityFlag(64, EntityFlag.SNEEZING);
        this.addEntityFlag(65, EntityFlag.TRUSTING);
        this.addEntityFlag(66, EntityFlag.ROLLING);
        this.addEntityFlag(67, EntityFlag.SCARED);
        this.addEntityFlag(68, EntityFlag.IN_SCAFFOLDING);
        this.addEntityFlag(69, EntityFlag.OVER_SCAFFOLDING);
        this.addEntityFlag(70, EntityFlag.FALL_THROUGH_SCAFFOLDING);
    }

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();

        this.addEntityEvent(73, EntityEventType.SUMMON_AGENT);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(239, BLOCK_BAMBOO_SAPLING_PLACE);
        this.addSoundEvent(240, PRE_SNEEZE);
        this.addSoundEvent(241, SNEEZE);
        this.addSoundEvent(242, AMBIENT_TAME);
        this.addSoundEvent(243, SCARED);
        this.addSoundEvent(244, BLOCK_SCAFFOLDING_CLIMB);
        this.addSoundEvent(245, CROSSBOW_LOADING_START);
        this.addSoundEvent(246, CROSSBOW_LOADING_MIDDLE);
        this.addSoundEvent(247, CROSSBOW_LOADING_END);
        this.addSoundEvent(248, CROSSBOW_SHOOT);
        this.addSoundEvent(249, CROSSBOW_QUICK_CHARGE_START);
        this.addSoundEvent(250, CROSSBOW_QUICK_CHARGE_MIDDLE);
        this.addSoundEvent(251, CROSSBOW_QUICK_CHARGE_END);
        this.addSoundEvent(252, AMBIENT_AGGRESSIVE);
        this.addSoundEvent(253, AMBIENT_WORRIED);
        this.addSoundEvent(254, CANT_BREED);
        this.addSoundEvent(255, UNDEFINED);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(11 + 3500, LevelEventType.AGENT_SPAWN_EFFECT);

        int legacy = 0x4000;
        this.addLevelEvent(45 + legacy, LevelEventType.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(46 + legacy, LevelEventType.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(47 + legacy, LevelEventType.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(48 + legacy, LevelEventType.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(49 + legacy, LevelEventType.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(50 + legacy, LevelEventType.PARTICLE_SPARKLER);
        this.addLevelEvent(51 + legacy, LevelEventType.PARTICLE_CONDUIT);
        this.addLevelEvent(52 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(53 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(54 + legacy, LevelEventType.PARTICLE_SNEEZE);
    }

    @Override
    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer.duplicate()));

        if (type == InventorySource.Type.UNTRACKED_INTERACTION_UI) {
            return InventorySource.fromUntrackedInteractionUI(VarInts.readInt(buffer));
        }
        return super.readSource(buffer);
    }
}
