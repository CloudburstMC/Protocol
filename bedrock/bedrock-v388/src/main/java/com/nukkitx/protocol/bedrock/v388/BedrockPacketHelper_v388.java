package com.nukkitx.protocol.bedrock.v388;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.v361.BedrockPacketHelper_v361;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v388 extends BedrockPacketHelper_v361 {
    public static final BedrockPacketHelper_v388 INSTANCE = new BedrockPacketHelper_v388();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(107, EntityData.AMBIENT_SOUND_INTERVAL);
        this.addEntityData(108, EntityData.AMBIENT_SOUND_INTERVAL_RANGE);
        this.addEntityData(109, EntityData.AMBIENT_SOUND_EVENT_NAME);
        this.addEntityData(110, EntityData.FALL_DAMAGE_MULTIPLIER);
        this.addEntityData(111, EntityData.NAME_RAW_TEXT);
        this.addEntityData(112, EntityData.CAN_RIDE_TARGET);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(88, EntityFlag.IS_IN_UI);
        this.addEntityFlag(89, EntityFlag.STALKING);
        this.addEntityFlag(90, EntityFlag.EMOTING);
        this.addEntityFlag(91, EntityFlag.CELEBRATING);
    }

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();

        this.addEntityEvent(74, EntityEventType.FINISHED_CHARGING_CROSSBOW);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        int particle = 2000;
        this.addLevelEvent(24 + particle, LevelEventType.PARTICLE_POINT_CLOUD);
        this.addLevelEvent(25 + particle, LevelEventType.PARTICLE_EXPLOSION);
        this.addLevelEvent(26 + particle, LevelEventType.PARTICLE_BLOCK_EXPLOSION);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(274, SoundEvent.AMBIENT_IN_RAID);
        this.addSoundEvent(275, SoundEvent.UI_CARTOGRAPHY_TABLE_USE);
        this.addSoundEvent(276, SoundEvent.UI_STONECUTTER_USE);
        this.addSoundEvent(277, SoundEvent.UI_LOOM_USE);
        this.addSoundEvent(278, SoundEvent.SMOKER_USE);
        this.addSoundEvent(279, SoundEvent.BLAST_FURNACE_USE);
        this.addSoundEvent(280, SoundEvent.SMITHING_TABLE_USE);
        this.addSoundEvent(281, SoundEvent.SCREECH);
        this.addSoundEvent(282, SoundEvent.SLEEP);
        this.addSoundEvent(283, SoundEvent.FURNACE_USE);
        this.addSoundEvent(284, SoundEvent.MOOSHROOM_CONVERT);
        this.addSoundEvent(285, SoundEvent.MILK_SUSPICIOUSLY);
        this.addSoundEvent(286, SoundEvent.CELEBRATE);
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer);

        int animationCount = buffer.readIntLE();
        List<AnimationData> animations = new ObjectArrayList<>(animationCount);
        for (int i = 0; i < animationCount; i++) {
            ImageData image = this.readImage(buffer);
            int type = buffer.readIntLE();
            float frames = buffer.readFloatLE();
            animations.add(new AnimationData(image, type, frames));
        }

        ImageData capeData = this.readImage(buffer);
        String geometryData = this.readString(buffer);
        String animationData = this.readString(buffer);
        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);

        return SerializedSkin.of(skinId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId);
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        buffer.writeIntLE(animations.size());
        for (AnimationData animation : animations) {
            this.writeImage(buffer, animation.getImage());
            buffer.writeIntLE(animation.getType());
            buffer.writeFloatLE(animation.getFrames());
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getAnimationData());
        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        this.writeString(buffer, skin.getCapeId());
        this.writeString(buffer, skin.getFullSkinId());
    }

    @Override
    public ImageData readImage(ByteBuf buffer) {
        requireNonNull(buffer, "buffer is null");
        int width = buffer.readIntLE();
        int height = buffer.readIntLE();
        byte[] image = readByteArray(buffer);
        return ImageData.of(width, height, image);
    }

    @Override
    public void writeImage(ByteBuf buffer, ImageData image) {
        requireNonNull(buffer, "buffer is null");
        requireNonNull(image, "image is null");

        buffer.writeIntLE(image.getWidth());
        buffer.writeIntLE(image.getHeight());
        writeByteArray(buffer, image.getImage());
    }
}
