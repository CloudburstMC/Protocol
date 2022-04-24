package com.nukkitx.protocol.bedrock.v503;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.command.CommandParam;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.structure.StructureAnimationMode;
import com.nukkitx.protocol.bedrock.data.structure.StructureMirror;
import com.nukkitx.protocol.bedrock.data.structure.StructureRotation;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v486.BedrockPacketHelper_v486;
import io.netty.buffer.ByteBuf;

public class BedrockPacketHelper_v503 extends BedrockPacketHelper_v486 {
    public static final BedrockPacketHelper_v503 INSTANCE = new BedrockPacketHelper_v503();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(125, EntityData.MOVEMENT_SOUND_DISTANCE_OFFSET);
        this.addEntityData(126, EntityData.HEARTBEAT_INTERVAL_TICKS);
        this.addEntityData(127, EntityData.HEARTBEAT_SOUND_EVENT);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(102, EntityFlag.JUMP_GOAL_JUMP);
        this.addEntityFlag(103, EntityFlag.EMERGING);
        this.addEntityFlag(104, EntityFlag.SNIFFING);
        this.addEntityFlag(105, EntityFlag.DIGGING);
    }

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();

        this.addEntityEvent(77, EntityEventType.VIBRATION_DETECTED);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(2000 + 37, LevelEventType.SCULK_CHARGE);
        this.addLevelEvent(2000 + 38, LevelEventType.SCULK_CHARGE_POP);
    }

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, CommandParam.INT);
        this.addCommandParam(3, CommandParam.FLOAT);
        this.addCommandParam(4, CommandParam.VALUE);
        this.addCommandParam(5, CommandParam.WILDCARD_INT);
        this.addCommandParam(6, CommandParam.OPERATOR);
        this.addCommandParam(7, CommandParam.TARGET);
        this.addCommandParam(9, CommandParam.WILDCARD_TARGET);

        this.addCommandParam(16, CommandParam.FILE_PATH);

        this.addCommandParam(38, CommandParam.STRING);
        this.addCommandParam(46, CommandParam.BLOCK_POSITION);
        this.addCommandParam(47, CommandParam.POSITION);
        this.addCommandParam(50, CommandParam.MESSAGE);
        this.addCommandParam(52, CommandParam.TEXT);
        this.addCommandParam(56, CommandParam.JSON);
        this.addCommandParam(69, CommandParam.COMMAND);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(375, SoundEvent.LISTENING);
        this.addSoundEvent(376, SoundEvent.HEARTBEAT);
        this.addSoundEvent(377, SoundEvent.HORN_BREAK);
        this.addSoundEvent(378, SoundEvent.SCULK_PLACE);
        this.addSoundEvent(379, SoundEvent.SCULK_SPREAD);
        this.addSoundEvent(380, SoundEvent.SCULK_CHARGE);
        this.addSoundEvent(381, SoundEvent.SCULK_SENSOR_PLACE);
        this.addSoundEvent(382, SoundEvent.SCULK_SHRIEKER_PLACE);
        this.addSoundEvent(383, SoundEvent.GOAT_CALL_0);
        this.addSoundEvent(384, SoundEvent.GOAT_CALL_1);
        this.addSoundEvent(385, SoundEvent.GOAT_CALL_2);
        this.addSoundEvent(386, SoundEvent.GOAT_CALL_3);
        this.addSoundEvent(387, SoundEvent.GOAT_CALL_4);
        this.addSoundEvent(388, SoundEvent.GOAT_CALL_5);
        this.addSoundEvent(389, SoundEvent.GOAT_CALL_6);
        this.addSoundEvent(390, SoundEvent.GOAT_CALL_7);
        this.addSoundEvent(391, SoundEvent.GOAT_CALL_8);
        this.addSoundEvent(392, SoundEvent.GOAT_CALL_9);
        this.addSoundEvent(393, SoundEvent.GOAT_HARMONY_0);
        this.addSoundEvent(394, SoundEvent.GOAT_HARMONY_1);
        this.addSoundEvent(395, SoundEvent.GOAT_HARMONY_2);
        this.addSoundEvent(396, SoundEvent.GOAT_HARMONY_3);
        this.addSoundEvent(397, SoundEvent.GOAT_HARMONY_4);
        this.addSoundEvent(398, SoundEvent.GOAT_HARMONY_5);
        this.addSoundEvent(399, SoundEvent.GOAT_HARMONY_6);
        this.addSoundEvent(400, SoundEvent.GOAT_HARMONY_7);
        this.addSoundEvent(401, SoundEvent.GOAT_HARMONY_8);
        this.addSoundEvent(402, SoundEvent.GOAT_HARMONY_9);
        this.addSoundEvent(403, SoundEvent.GOAT_MELODY_0);
        this.addSoundEvent(404, SoundEvent.GOAT_MELODY_1);
        this.addSoundEvent(405, SoundEvent.GOAT_MELODY_2);
        this.addSoundEvent(406, SoundEvent.GOAT_MELODY_3);
        this.addSoundEvent(407, SoundEvent.GOAT_MELODY_4);
        this.addSoundEvent(408, SoundEvent.GOAT_MELODY_5);
        this.addSoundEvent(409, SoundEvent.GOAT_MELODY_6);
        this.addSoundEvent(410, SoundEvent.GOAT_MELODY_7);
        this.addSoundEvent(411, SoundEvent.GOAT_MELODY_8);
        this.addSoundEvent(412, SoundEvent.GOAT_MELODY_9);
        this.addSoundEvent(413, SoundEvent.GOAT_BASS_0);
        this.addSoundEvent(414, SoundEvent.GOAT_BASS_1);
        this.addSoundEvent(415, SoundEvent.GOAT_BASS_2);
        this.addSoundEvent(416, SoundEvent.GOAT_BASS_3);
        this.addSoundEvent(417, SoundEvent.GOAT_BASS_4);
        this.addSoundEvent(418, SoundEvent.GOAT_BASS_5);
        this.addSoundEvent(419, SoundEvent.GOAT_BASS_6);
        this.addSoundEvent(420, SoundEvent.GOAT_BASS_7);
        this.addSoundEvent(421, SoundEvent.GOAT_BASS_8);
        this.addSoundEvent(422, SoundEvent.GOAT_BASS_9);
        this.addSoundEvent(423, SoundEvent.UNDEFINED);
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        boolean nonTickingPlayersAndTickingAreasEnabled = buffer.readBoolean();
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

        return new StructureSettings(paletteName, ignoringEntities, ignoringBlocks,
                nonTickingPlayersAndTickingAreasEnabled, size, offset, lastEditedByEntityId, rotation, mirror,
                animationMode, animationSeconds, integrityValue, integritySeed, pivot);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        this.writeString(buffer, settings.getPaletteName());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        buffer.writeBoolean(settings.isIgnoringBlocks());
        buffer.writeBoolean(settings.isNonTickingPlayersAndTickingAreasEnabled());
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
