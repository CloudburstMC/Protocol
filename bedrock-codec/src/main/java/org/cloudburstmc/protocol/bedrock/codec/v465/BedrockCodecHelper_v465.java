package org.cloudburstmc.protocol.bedrock.codec.v465;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v448.BedrockCodecHelper_v448;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v465 extends BedrockCodecHelper_v448 {

    public BedrockCodecHelper_v465(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes, TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String playFabId = this.readString(buffer);
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer, ImageData.SKIN_PERSONA_SIZE);

        List<AnimationData> animations = new ObjectArrayList<>();
        this.readArray(buffer, animations, ByteBuf::readIntLE, (b, h) -> this.readAnimationData(b));

        ImageData capeData = this.readImage(buffer, ImageData.SINGLE_SKIN_SIZE);
        String geometryData = this.readString(buffer);
        String geometryDataEngineVersion = this.readString(buffer);
        String animationData = this.readString(buffer);
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);
        String armSize = this.readString(buffer);
        String skinColor = this.readString(buffer);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        this.readArray(buffer, personaPieces, ByteBuf::readIntLE, (buf, h) -> {
            String pieceId = this.readString(buf);
            String pieceType = this.readString(buf);
            String packId = this.readString(buf);
            boolean isDefault = buf.readBoolean();
            String productId = this.readString(buf);
            return new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId);
        });

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        this.readArray(buffer, tintColors, ByteBuf::readIntLE, (buf, h) -> {
            String pieceType = this.readString(buf);
            List<String> colors = new ObjectArrayList<>();
            int colorsLength = buf.readIntLE();
            for (int i2 = 0; i2 < colorsLength; i2++) {
                colors.add(this.readString(buf));
            }
            return new PersonaPieceTintData(pieceType, colors);
        });

        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        boolean primaryUser = buffer.readBoolean();

        return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, geometryDataEngineVersion,
                animationData, premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getPlayFabId());
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        buffer.writeIntLE(animations.size());
        for (AnimationData animation : animations) {
            this.writeAnimationData(buffer, animation);
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getGeometryDataEngineVersion());
        this.writeString(buffer, skin.getAnimationData());
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

        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        buffer.writeBoolean(skin.isPrimaryUser());
    }
}
