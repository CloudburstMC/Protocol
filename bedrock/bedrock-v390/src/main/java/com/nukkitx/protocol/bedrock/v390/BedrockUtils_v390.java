package com.nukkitx.protocol.bedrock.v390;

import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.*;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class BedrockUtils_v390 {

    public static SerializedSkin readSkin(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String skinId = BedrockUtils.readString(buffer);
        String skinResourcePatch = BedrockUtils.readString(buffer);
        ImageData skinData = BedrockUtils.readImageData(buffer);

        int animationCount = buffer.readIntLE();
        List<AnimationData> animations = new ObjectArrayList<>(animationCount);
        for (int i = 0; i < animationCount; i++) {
            ImageData image = BedrockUtils.readImageData(buffer);
            int type = buffer.readIntLE();
            float frames = buffer.readFloatLE();
            animations.add(new AnimationData(image, type, frames));
        }

        ImageData capeData = BedrockUtils.readImageData(buffer);
        String geometryData = BedrockUtils.readString(buffer);
        String animationData = BedrockUtils.readString(buffer);
        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        String capeId = BedrockUtils.readString(buffer);
        String fullSkinId = BedrockUtils.readString(buffer);
        String armSize = BedrockUtils.readString(buffer);
        String skinColor = BedrockUtils.readString(buffer);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        int piecesLength = buffer.readIntLE();
        for (int i = 0; i < piecesLength; i++) {
            String pieceId = BedrockUtils.readString(buffer);
            String pieceType = BedrockUtils.readString(buffer);
            String packId = BedrockUtils.readString(buffer);
            boolean isDefault = buffer.readBoolean();
            String productId = BedrockUtils.readString(buffer);
            personaPieces.add(new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId));
        }

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        int tintsLength = buffer.readIntLE();
        for (int i = 0; i < tintsLength; i++) {
            String pieceType = BedrockUtils.readString(buffer);
            List<String> colors = new ObjectArrayList<>();
            int colorsLength = buffer.readIntLE();
            for (int i2 = 0; i2 < colorsLength; i2++) {
                colors.add(BedrockUtils.readString(buffer));
            }
            tintColors.add(new PersonaPieceTintData(pieceType, colors));
        }

        return SerializedSkin.of(skinId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData,
                premium, persona, capeOnClassic, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    public static void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(skin, "skin");

        BedrockUtils.writeString(buffer, skin.getSkinId());
        BedrockUtils.writeString(buffer, skin.getSkinResourcePatch());
        BedrockUtils.writeImageData(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        buffer.writeIntLE(animations.size());
        for (AnimationData animation : animations) {
            BedrockUtils.writeImageData(buffer, animation.getImage());
            buffer.writeIntLE(animation.getType());
            buffer.writeFloatLE(animation.getFrames());
        }

        BedrockUtils.writeImageData(buffer, skin.getCapeData());
        BedrockUtils.writeString(buffer, skin.getGeometryData());
        BedrockUtils.writeString(buffer, skin.getAnimationData());
        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        BedrockUtils.writeString(buffer, skin.getCapeId());
        BedrockUtils.writeString(buffer, skin.getFullSkinId());
        BedrockUtils.writeString(buffer, skin.getArmSize());
        BedrockUtils.writeString(buffer, skin.getSkinColor());
        List<PersonaPieceData> pieces = skin.getPersonaPieces();
        buffer.writeIntLE(pieces.size());
        for (PersonaPieceData piece : pieces) {
            BedrockUtils.writeString(buffer, piece.getId());
            BedrockUtils.writeString(buffer, piece.getType());
            BedrockUtils.writeString(buffer, piece.getPackId());
            buffer.writeBoolean(piece.isDefault());
            BedrockUtils.writeString(buffer, piece.getProductId());
        }

        List<PersonaPieceTintData> tints = skin.getTintColors();
        buffer.writeIntLE(tints.size());
        for (PersonaPieceTintData tint : tints) {
            BedrockUtils.writeString(buffer, tint.getType());
            List<String> colors = tint.getColors();
            buffer.writeIntLE(colors.size());
            for (String color : colors) {
                BedrockUtils.writeString(buffer, color);
            }
        }
    }
}
