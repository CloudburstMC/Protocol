package com.nukkitx.protocol.bedrock.v440;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.structure.StructureAnimationMode;
import com.nukkitx.protocol.bedrock.data.structure.StructureMirror;
import com.nukkitx.protocol.bedrock.data.structure.StructureRotation;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v431.BedrockPacketHelper_v431;
import io.netty.buffer.ByteBuf;

public class BedrockPacketHelper_v440 extends BedrockPacketHelper_v431 {
    public static final BedrockPacketHelper_v440 INSTANCE = new BedrockPacketHelper_v440();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(120, EntityData.BASE_RUNTIME_ID);
        this.addEntityData(121, EntityData.FREEZING_EFFECT_STRENGTH);
        this.addEntityData(122, EntityData.BUOYANCY_DATA);
        this.addEntityData(123, EntityData.GOAT_HORN_COUNT);
        this.addEntityData(124, EntityData.UPDATE_PROPERTIES);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        addEntityFlag(97, EntityFlag.PLAYING_DEAD);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        int legacy = 0x4000;
        this.addLevelEvent(73 + legacy, LevelEventType.PARTICLE_PORTAL_REVERSE);
        this.addLevelEvent(74 + legacy, LevelEventType.PARTICLE_SNOWFLAKE);
        this.addLevelEvent(75 + legacy, LevelEventType.PARTICLE_VIBRATION_SIGNAL);
        this.addLevelEvent(76 + legacy, LevelEventType.PARTICLE_SCULK_SENSOR_REDSTONE);
        this.addLevelEvent(77 + legacy, LevelEventType.PARTICLE_SPORE_BLOSSOM_SHOWER);
        this.addLevelEvent(78 + legacy, LevelEventType.PARTICLE_SPORE_BLOSSOM_AMBIENT);
        this.addLevelEvent(79 + legacy, LevelEventType.PARTICLE_WAX);
        this.addLevelEvent(80 + legacy, LevelEventType.PARTICLE_ELECTRIC_SPARK);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(339, SoundEvent.COPPER_WAX_ON);
        this.addSoundEvent(340, SoundEvent.COPPER_WAX_OFF);
        this.addSoundEvent(341, SoundEvent.SCRAPE);
        this.addSoundEvent(342, SoundEvent.PLAYER_HURT_DROWN);
        this.addSoundEvent(343, SoundEvent.PLAYER_HURT_ON_FIRE);
        this.addSoundEvent(344, SoundEvent.PLAYER_HURT_FREEZE);
        this.addSoundEvent(345, SoundEvent.USE_SPYGLASS);
        this.addSoundEvent(346, SoundEvent.STOP_USING_SPYGLASS);
        this.addSoundEvent(347, SoundEvent.AMETHYST_BLOCK_CHIME);
        this.addSoundEvent(348, SoundEvent.AMBIENT_SCREAMER);
        this.addSoundEvent(349, SoundEvent.HURT_SCREAMER);
        this.addSoundEvent(350, SoundEvent.DEATH_SCREAMER);
        this.addSoundEvent(351, SoundEvent.MILK_SCREAMER);
        this.addSoundEvent(352, SoundEvent.JUMP_TO_BLOCK);
        this.addSoundEvent(353, SoundEvent.PRE_RAM);
        this.addSoundEvent(354, SoundEvent.PRE_RAM_SCREAMER);
        this.addSoundEvent(355, SoundEvent.RAM_IMPACT);
        this.addSoundEvent(356, SoundEvent.RAM_IMPACT_SCREAMER);
        this.addSoundEvent(357, SoundEvent.SQUID_INK_SQUIRT);
        this.addSoundEvent(358, SoundEvent.GLOW_SQUID_INK_SQUIRT);
        this.addSoundEvent(359, SoundEvent.CONVERT_TO_STRAY);
        this.addSoundEvent(360, SoundEvent.UNDEFINED);
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(gameRule, "gameRule");

        Object value = gameRule.getValue();
        int type = this.gameRuleTypes.getInt(value.getClass());

        writeString(buffer, gameRule.getName());
        buffer.writeBoolean(gameRule.isEditable());
        VarInts.writeUnsignedInt(buffer, type);
        switch (type) {
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                VarInts.writeUnsignedInt(buffer, (int) value);
                break;
            case 3:
                buffer.writeFloatLE((float) value);
                break;
        }
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);
        boolean editable = buffer.readBoolean();
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 1:
                return new GameRuleData<>(name, editable, buffer.readBoolean());
            case 2:
                return new GameRuleData<>(name, editable, VarInts.readUnsignedInt(buffer));
            case 3:
                return new GameRuleData<>(name, editable, buffer.readFloatLE());
        }
        throw new IllegalStateException("Invalid gamerule type received");
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        Vector3i size = this.readBlockPosition(buffer);
        Vector3i offset = this.readBlockPosition(buffer);
        long lastEditedByEntityId = VarInts.readLong(buffer);
        StructureRotation rotation = StructureRotation.from(buffer.readByte());
        StructureMirror mirror = StructureMirror.from(buffer.readByte());
        StructureAnimationMode animationMode = StructureAnimationMode.from(buffer.readUnsignedByte());
        float animationSeconds = buffer.readFloatLE();
        float integrityValue = buffer.readFloatLE();
        int integritySeed = buffer.readIntLE();
        Vector3f pivot = this.readVector3f(buffer);

        return new StructureSettings(paletteName, ignoringEntities, ignoringBlocks, true, size, offset, lastEditedByEntityId,
                rotation, mirror, animationMode, animationSeconds, integrityValue, integritySeed, pivot);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        this.writeString(buffer, settings.getPaletteName());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        this.writeBlockPosition(buffer, settings.getSize());
        this.writeBlockPosition(buffer, settings.getOffset());
        VarInts.writeLong(buffer, settings.getLastEditedByEntityId());
        buffer.writeByte(settings.getRotation().ordinal());
        buffer.writeByte(settings.getMirror().ordinal());
        buffer.writeByte(settings.getAnimationMode().ordinal());
        buffer.writeFloatLE(settings.getAnimationSeconds());
        buffer.writeFloatLE(settings.getIntegrityValue());
        buffer.writeIntLE(settings.getIntegritySeed());
        this.writeVector3f(buffer, settings.getPivot());
    }
}
