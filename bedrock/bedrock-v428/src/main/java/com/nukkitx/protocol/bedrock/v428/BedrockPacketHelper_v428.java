package com.nukkitx.protocol.bedrock.v428;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.skin.*;
import com.nukkitx.protocol.bedrock.v422.BedrockPacketHelper_v422;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;
import static java.util.Objects.requireNonNull;

public class BedrockPacketHelper_v428 extends BedrockPacketHelper_v422 {
    public static final BedrockPacketHelper_v428 INSTANCE = new BedrockPacketHelper_v428();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        int index = 60;
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET); // goodness why
        this.addEntityData(index++, EntityData.AREA_EFFECT_CLOUD_RADIUS);
        this.addEntityData(index++, EntityData.AREA_EFFECT_CLOUD_WAITING);
        this.addEntityData(index++, EntityData.AREA_EFFECT_CLOUD_PARTICLE_ID);
        this.addEntityData(index++, EntityData.SHULKER_PEEK_ID);
        this.addEntityData(index++, EntityData.SHULKER_ATTACH_FACE);
        this.addEntityData(index++, EntityData.SHULKER_ATTACH_POS);
        this.addEntityData(index++, EntityData.TRADE_TARGET_EID);
        this.addEntityData(index++, EntityData.MARK_VARIANT);
        this.addEntityData(index++, EntityData.COMMAND_BLOCK_ENABLED);
        this.addEntityData(index++, EntityData.COMMAND_BLOCK_COMMAND);
        this.addEntityData(index++, EntityData.COMMAND_BLOCK_TRACK_OUTPUT);
        this.addEntityData(index++, EntityData.CONTROLLING_RIDER_SEAT_INDEX);
        this.addEntityData(index++, EntityData.STRENGTH);
        this.addEntityData(index++, EntityData.MAX_STRENGTH);
        this.addEntityData(index++, EntityData.EVOKER_SPELL_COLOR);
        this.addEntityData(index++, EntityData.LIMITED_LIFE);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
        this.addEntityData(index++, EntityData.RIDER_ROTATION_OFFSET);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(96, EntityFlag.RAM_ATTACK);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(2027, LevelEventType.PARTICLE_VIBRATION_SIGNAL);

        this.addLevelEvent(3514, LevelEventType.CAULDRON_FILL_POWDER_SNOW);
        this.addLevelEvent(3515, LevelEventType.CAULDRON_TAKE_POWDER_SNOW);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(318, SoundEvent.AMBIENT_LOOP_WARPED_FOREST);
        this.addSoundEvent(319, SoundEvent.AMBIENT_LOOP_SOULSAND_VALLEY);
        this.addSoundEvent(320, SoundEvent.AMBIENT_LOOP_NETHER_WASTES);
        this.addSoundEvent(321, SoundEvent.AMBIENT_LOOP_BASALT_DELTAS);
        this.addSoundEvent(322, SoundEvent.AMBIENT_LOOP_CRIMSON_FOREST);
        this.addSoundEvent(323, SoundEvent.AMBIENT_ADDITION_WARPED_FOREST);
        this.addSoundEvent(324, SoundEvent.AMBIENT_ADDITION_SOULSAND_VALLEY);
        this.addSoundEvent(325, SoundEvent.AMBIENT_ADDITION_NETHER_WASTES);
        this.addSoundEvent(326, SoundEvent.AMBIENT_ADDITION_BASALT_DELTAS);
        this.addSoundEvent(327, SoundEvent.AMBIENT_ADDITION_CRIMSON_FOREST);
        this.addSoundEvent(328, SoundEvent.SCULK_SENSOR_POWER_ON);
        this.addSoundEvent(329, SoundEvent.SCULK_SENSOR_POWER_OFF);
        this.addSoundEvent(330, SoundEvent.BUCKET_FILL_POWDER_SNOW); // to check
        this.addSoundEvent(331, SoundEvent.BUCKET_EMPTY_POWDER_SNOW);
        this.addSoundEvent(332, SoundEvent.UNDEFINED);
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
        this.stackRequestActionTypes.put(9, MINE_BLOCK); // new for v428
        this.stackRequestActionTypes.put(10, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(11, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(12, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(13, CRAFT_RECIPE_OPTIONAL);
        this.stackRequestActionTypes.put(14, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(15, CRAFT_RESULTS_DEPRECATED);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String playFabId = this.readString(buffer); // new for v428
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer);

        int animationCount = buffer.readIntLE();
        List<AnimationData> animations = new ObjectArrayList<>(animationCount);
        for (int i = 0; i < animationCount; i++) {
            animations.add(this.readAnimationData(buffer));
        }

        ImageData capeData = this.readImage(buffer);
        String geometryData = this.readString(buffer);
        String animationData = this.readString(buffer);
        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);
        String armSize = this.readString(buffer);
        String skinColor = this.readString(buffer);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        int piecesLength = buffer.readIntLE();
        for (int i = 0; i < piecesLength; i++) {
            String pieceId = this.readString(buffer);
            String pieceType = this.readString(buffer);
            String packId = this.readString(buffer);
            boolean isDefault = buffer.readBoolean();
            String productId = this.readString(buffer);
            personaPieces.add(new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId));
        }

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        int tintsLength = buffer.readIntLE();
        for (int i = 0; i < tintsLength; i++) {
            String pieceType = this.readString(buffer);
            List<String> colors = new ObjectArrayList<>();
            int colorsLength = buffer.readIntLE();
            for (int i2 = 0; i2 < colorsLength; i2++) {
                colors.add(this.readString(buffer));
            }
            tintColors.add(new PersonaPieceTintData(pieceType, colors));
        }

        return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getPlayFabId()); // new for v428
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        buffer.writeIntLE(animations.size());
        for (AnimationData animation : animations) {
            this.writeAnimationData(buffer, animation);
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getAnimationData());
        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        this.writeString(buffer, skin.getCapeId());
        this.writeString(buffer, skin.getFullSkinId());
        this.writeString(buffer, skin.getArmSize());
        this.writeString(buffer, skin.getSkinColor());
        List<PersonaPieceData> pieces = skin.getPersonaPieces();
        buffer.writeIntLE(pieces.size());
        for (PersonaPieceData piece : pieces) {
            this.writeString(buffer, piece.getId());
            this.writeString(buffer, piece.getType());
            this.writeString(buffer, piece.getPackId());
            buffer.writeBoolean(piece.isDefault());
            this.writeString(buffer, piece.getProductId());
        }

        List<PersonaPieceTintData> tints = skin.getTintColors();
        buffer.writeIntLE(tints.size());
        for (PersonaPieceTintData tint : tints) {
            this.writeString(buffer, tint.getType());
            List<String> colors = tint.getColors();
            buffer.writeIntLE(colors.size());
            for (String color : colors) {
                this.writeString(buffer, color);
            }
        }
    }
}
